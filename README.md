# ChefYa

Le muestras tu nevera y te dice qué cocinar: recetas caseras latinas, de 30 minutos o menos,
con lo que ya tienes. Android nativo en Kotlin y Jetpack Compose, en español e inglés.
Hecho por lacosta.agency.

## Qué hace esta versión (0.1)

- **Inicio**: anotas lo que tienes (reconoce nombres de cada país: caraotas, frijoles, porotos…)
  y te muestra las recetas que puedes hacer ya o a las que les faltan una o dos cosas.
- **Recetas**: 20 recetas incluidas en la app, funcionan sin internet.
- **Receta**: qué tienes y qué te falta, con un botón para mandar lo que falta a Compras.
- **Compras**: al marcar algo como comprado pasa a tu cocina.
- **Ajustes**: tema claro u oscuro, plan y crédito.

Todo se guarda solo en el teléfono (DataStore). La foto con IA llega después, con un servidor
pequeño que guarda la clave y controla el límite gratis por día.

## Probar en el teléfono

Cada cambio en `main` publica el APK en el release **prueba**:
https://github.com/cariteshopve-dot/chefya/releases/download/prueba/ChefYa-prueba.apk

Abre el link en Chrome (no dentro de otra app) e instálalo.

## Compilar

```
./gradlew testDebugUnitTest assembleDebug
```

La fuente Fredoka va con su licencia en `licenses/OFL-Fredoka.txt`.
