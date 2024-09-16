Todas las pruebas se han realizado en una terminal sin interfaz gráfica (tty4), sin ninguna conexión a puertos del
ordenador, modo avión (sin internet/bluetooth), sin aplicaciones abiertas, rendimiento
17%-100%, brillo al 100%, luz del teclado apagada, y con el cargador conectado.

C

|     |         Normal          |         OpenMP          |
|:---:|:-----------------------:|:-----------------------:|
| -O0 | 44.520s 44.690s 44.770s | 13.490s 13.490s 13.690s |
| -O1 | 24.450s 24.400s 24.420s |  7.367s 7.380s 7.387s   |
| -O2 | 24.390s 24.360s 24.400s |  7.432s 7.430s 7.473s   |
| -O3 | 24.290s 24.290s 24.280s |  7.363s 7.409s 7.404s   |

Java

|         Normal          |        JROMP         |
|:-----------------------:|:--------------------:|
| 24.790s 24.680s 24.890s | 8.033s 8.280s 8.252s |
