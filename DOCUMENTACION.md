# Documentación técnica — Adivina Quién (Programación 3)

Este documento justifica las decisiones algorítmicas y de diseño del proyecto, pensado para poder defenderlo ante el docente.

## 1. Modelo de datos

`Personaje` (`objetos/Personaje.java`) tiene 6 atributos booleanos, ya definidos desde el inicio del proyecto y mantenidos tal cual: `genero`, `anteojos`, `sombrero`, `barba`, `sonrisa`, `pelo_largo`. Se decidió conservar estos atributos (en vez de reemplazarlos por otro set) porque ya formaban parte de la base del proyecto y son suficientes para tener un espacio de búsqueda interesante: con 6 atributos booleanos hay hasta 2⁶ = 64 combinaciones posibles, más que suficiente para distinguir 23 personajes.

`Generador` (`bd/Generador.java`) crea los 23 personajes agrupados **únicamente por género** (primero los 8 hombres, después las 15 mujeres, sin ningún orden alfabético dentro de cada bloque), tal como pide la consigna. Ese es el punto de partida que después ordena la máquina.

## 2. Divide y Conquista — ordenar el tablero

**Problema**: la consigna pide que la máquina tome la lista agrupada solo por género y la disponga en una lista ordenada, asignando los ids de forma autoincremental según el orden final.

**Solución — `algoritmo/OrdenadorMergeSort.java`**: Merge Sort clásico.

- **Divide**: la lista se parte recursivamente por la mitad hasta llegar a sublistas de un solo elemento (que ya están ordenadas por definición).
- **Conquista/combina**: se van mezclando de a pares, comparando nombre contra nombre, hasta reconstruir la lista completa ordenada alfabéticamente.
- Al terminar, se recorre la lista final asignando `id = posición + 1` — ese es el "de forma autoincremental según se agregan los personajes" de la consigna.

**Por qué Merge Sort y no un sort ingenuo (Bubble/Selection sort)**: Merge Sort es *el* algoritmo canónico de divide y conquista para ordenamiento (partir el problema en subproblemas más chicos del mismo tipo, resolverlos por separado, combinar los resultados), con complejidad **O(n log n)** garantizada en el peor caso, contra **O(n²)** de un sort ingenuo. Con 23 elementos la diferencia de performance es irrelevante en la práctica — la elección se justifica porque es la que mejor representa el concepto de divide y conquista visto en la materia, no por necesidad de performance.

## 3. Divide y Conquista — verificar una suposición

**Problema**: en cualquier momento, un jugador puede "lanzar su suposición" (adivinar un nombre directamente). Hay que ubicar ese personaje en el tablero para validar la jugada.

**Solución — `algoritmo/BuscadorBinario.java`**: búsqueda binaria sobre la lista ya ordenada por `OrdenadorMergeSort`. En cada paso compara el nombre buscado contra el elemento del medio del rango actual y descarta la mitad del rango que no puede contener la respuesta, hasta encontrarlo o agotar el rango.

**Por qué búsqueda binaria y no lineal**: la búsqueda binaria es **O(log n)** porque, al igual que Merge Sort, divide el problema en mitades y descarta una en cada paso (divide y conquista aplicado a búsqueda). Una búsqueda lineal sería O(n) — funciona, pero no aplica el concepto de la materia. Además, **requiere que la lista esté ordenada primero**, que es exactamente lo que entrega el paso anterior: los dos algoritmos de divide y conquista del proyecto están encadenados a propósito (primero se ordena, después se puede buscar en O(log n)).

## 4. Greedy — elegir la próxima pregunta

**Problema**: en su turno, la IA tiene que decidir qué filtro preguntar para reducir lo más posible la cantidad de candidatos que le quedan por adivinar.

**Solución — `algoritmo/PonderadoCaracteristicas.java`**: para cada uno de los 6 filtros posibles, cuenta cuántos de los candidatos restantes responden "sí" y cuántos "no" a esa pregunta, y calcula la diferencia entre ambos conteos. Elige el filtro cuya diferencia sea **mínima**, es decir, el que divide a los candidatos lo más parejo posible entre sí/no.

Esta clase ya existía parcialmente en el proyecto (contaba ocurrencias por atributo usando `Caracteristica`), pero le faltaba el paso de comparar los atributos entre sí para elegir uno solo — solo calculaba la mayoría de cada atributo por separado, repitiendo el mismo bloque de código 6 veces. Se completó esa idea y se generalizó usando el enum `Filtro` (que une cada atributo con su pregunta en español) para eliminar la repetición.

**Por qué es greedy**: en cada turno se toma la decisión que es **localmente óptima** — la que maximiza el descarte esperado *en ese turno puntual* — sin mirar más de un paso hacia adelante. Es la definición clásica de un algoritmo voraz: construir la solución paso a paso, eligiendo en cada paso lo que parece mejor en ese momento, sin reconsiderar decisiones previas ni planificar varios pasos a la vez.

**Complejidad**: O(candidatos × filtros) por turno — para cada uno de los 6 filtros, recorre la lista de candidatos una vez.

## 5. Encapsulamiento del secreto

La consigna exige explícitamente que la máquina **no pueda acceder directamente** a la variable donde el humano guarda su personaje elegido. Esto se resuelve con la interfaz `juego/Jugador.java`:

```java
public interface Jugador {
    boolean responder(Filtro filtro);
    boolean esElSecreto(Personaje candidato);
    Personaje getSecretoParaMostrar();
    String getNombreJugador();
}
```

`Humano` y `MaquinaIA` guardan su propio `secreto` como campo **privado**. Ningún otro objeto (ni el rival, ni `PartidaHumanoVsIA`/`PartidaIaVsIa`, ni el algoritmo greedy) puede leerlo directamente: solo pueden llamar a `responder(filtro)` (para hacer una pregunta) o `esElSecreto(candidato)` (para validar una suposición puntual). El único lugar donde se expone el secreto real es `getSecretoParaMostrar()`, usado exclusivamente al finalizar la partida para mostrar el resultado.

Esto es verificable con un simple `grep` del campo `secreto`: aparece únicamente dentro de `Humano.java` y `MaquinaIA.java`, nunca en `PartidaHumanoVsIA`, `PartidaIaVsIa` ni en la UI.

## 6. Seguimiento en tiempo real por consola

Como el objetivo del trabajo es poder **ver el proceso**, no solo el resultado, se agregó `util/Trazador.java`: cada algoritmo (`PonderadoCaracteristicas`, `OrdenadorMergeSort`, `BuscadorBinario`) y la orquestación de turnos (`PartidaHumanoVsIA`, `PartidaIaVsIa`) imprimen por consola, en tiempo real, sus decisiones internas cada vez que un botón de la interfaz dispara esa lógica: qué filtro está evaluando el greedy y por qué, cómo se va dividiendo y mezclando la lista en el Merge Sort, qué mitad descarta la búsqueda binaria en cada paso, y cada pregunta/respuesta/adivinanza de cada turno. Esto da un segundo canal de verificación, independiente de la interfaz gráfica, útil para mostrar en la defensa oral consola y ventana en simultáneo.

## 7. Qué NO se aplicó, y por qué

- **Árbol de decisión óptimo global (tipo minimax o ID3)**: en vez de elegir la pregunta greedy más pareja en cada turno, se podría construir el árbol completo de todas las secuencias posibles de preguntas y elegir la que minimiza el número de preguntas en el peor o promedio de los casos. Es una solución más óptima, pero exponencialmente más compleja de implementar y de explicar, y no aporta un beneficio práctico visible con solo 23 personajes — el enfoque greedy converge en pocos turnos igual. Se priorizó una solución simple, correcta y fácil de defender.
- **Backtracking**: no hace falta deshacer decisiones tomadas, porque el juego nunca "vuelve atrás" sobre una pregunta ya respondida — cada filtrado de candidatos es permanente dentro de una partida. Por eso no aplica.
- **Programación dinámica**: no hay subproblemas superpuestos que convenga memoizar; cada partida es una secuencia lineal de decisiones sobre un conjunto de candidatos que solo se achica.

## 8. Cómo correr el proyecto

Desde IntelliJ (ya incluye el `.idea` del proyecto): abrir `Main.java` y ejecutar. También puede compilarse y correrse por línea de comandos con `javac`/`java` sobre `src/main/java`, apuntando la clase principal a `org.example.Main`. No hace falta ninguna dependencia extra: Swing es parte del JDK.
