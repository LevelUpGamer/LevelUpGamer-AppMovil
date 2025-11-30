# Level Up Gamer - Proyecto Académico de Aplicación Móvil

## Descripción del Proyecto
Level Up Gamer es una aplicación móvil desarrollada en Kotlin utilizando Android Studio y Jetpack Compose, siguiendo el patrón de arquitectura MVVM.
Permite:
- Navegar por un catálogo de productos gamer
- Buscar juegos en tiempo real
- Agregar productos al carrito
- Quitar unidades o eliminar ítems completos
- Hacer una simulación de pago (limpiando el carrito)
- Ver noticias gamer en tiempo real usando una API externa (MMOBomb)
- Iniciar sesión y registrarse mediante almacenamiento local (SharedPreferences)
- Usar un menú lateral (Drawer) con navegación entre pantallas
- Disfrutar una experiencia visual personalizada con tema propio y vibración háptica

## Integrantes
- Aran Opazo 
- Isabel Sánchez

## Funcionalidades Implementadas

**Autenticación Local**
- Pantalla de inicio de sesión que valida credenciales almacenadas mediante SharedPreferences.
- Pantalla de registro con validación de correo electrónico y contraseña segura.
- Manejo de mensajes de error y retorno al login después de registrarse.

**Navegación Principal**
- Implementación de un Drawer lateral que organiza la navegación entre las secciones principales:
  - Home
  - Catálogo 
  - Carrito 
  - Cerrar sesión 
- Gestión del estado del Drawer y del elemento seleccionado mediante el ViewModel principal.

**Catálogo de Productos**
- Listado de productos con imagen, nombre y precio formateado. 
- Cada producto puede ser agregado al carrito. 
- Si el producto ya está en el carrito, se muestran controles para sumar o restar unidades. 
- Función de búsqueda integrada que filtra los resultados en tiempo real.

**Carrito de Compras**
- Sistema para agregar unidades, quitar unidades o eliminar un producto completamente. 
- Cálculo automático del total a pagar. 
- Efecto de vibración para retroalimentación táctil en las acciones principales. 
- Limpieza del carrito al completar el proceso de pago.

**Noticias Gamer (API Externa)**
- Consumo de datos desde la API pública de MMOBomb usando Retrofit. 
- Vista de noticias que incluye título, descripción breve, imagen miniatura y enlace al artículo completo.
- Manejo de estados: cargando, error de conexión o lista vacía.

**Pantalla Splash**
- Pantalla inicial con animación de escala en el logotipo. 
- Redirección automática hacia la pantalla de inicio de sesión.

**Estilo y Tematización**
- Tema personalizado utilizando Material 3. 
- Uso de una paleta cromática basada en tonos púrpura, negro, blanco y verde acento. 
- Aplicación coherente del esquema de colores en todas las pantallas principales. 
- Componentes adaptados a Compose (Cards, Buttons, TextFields, etc.).

## Arquitectura y Organización
La aplicación sigue el patrón MVVM:
- **ViewModel:** manejo centralizado del estado global, control del Drawer, carrito de compras, búsqueda, navegación interna y carga de noticias desde la API. 
- **UI (Compose):** pantallas modulares implementadas con Jetpack Compose, sin uso de fragments ni activities adicionales en exceso. 
- **Data:** uso de Retrofit para obtener las noticias y modelo de datos para representar productos y artículos. 
- **Utils:** funciones auxiliares para vibración háptica y formateo de monedas.
- **View:** actividades responsables de inicializar la interfaz (Splash, Login, Registro, Drawer).
Esta organización facilita el mantenimiento, escalabilidad y claridad en la responsabilidad de cada módulo.

## Tecnologías Utilizadas
- Kotlin
- Jetpack Compose
- Material Design 3
- Retrofit + Gson
- Coil (carga de imágenes remotas)
- Coroutines 
- SharedPreferences
- StateFlow y mutableStateOf
- Compose Navigation

## Pruebas Unitarias
El proyecto incluye un conjunto de pruebas unitarias desarrolladas con JUnit y herramientas auxiliares como MockWebServer, MockK y Test Dispatchers de Kotlin. Estas pruebas permiten validar el comportamiento de la lógica del ViewModel, la navegación, el consumo de la API externa y funciones utilitarias.

Las pruebas se encuentran en el directorio estándar app/src/test/java/com/example/levelupgamer/.

A continuación se detalla lo validado en cada módulo:

**1. Pruebas del servicio de noticias (API MMOBomb)**
   
Archivo: MmoNewsApiServiceTest.kt
   - Validación del parseo correcto del JSON recibido desde MockWebServer. 
   - Verificación de que los campos del modelo se asignan correctamente.

**2. Pruebas del sistema de navegación**
   
Archivo: NavigationEventTest.kt
   - Validación de creación de eventos de navegación. 
   - Confirmación del uso correcto de parámetros como popUpToRoute, inclusive y singleTop. 
   - Comprobación de los objetos singleton PopBackStack y NavigateUp.

Archivo: ScreenTest.kt
   - Verificación de que cada pantalla posee la ruta correcta.

**3. Pruebas del ViewModel de Noticias**
   
Archivo: NoticiasViewModelTest.kt
   - Validación de actualización correcta del estado en caso de éxito. 
   - Control de errores cuando la API arroja excepciones. 
   - Uso de un dispatcher de prueba para el manejo determinista de coroutines.

**4. Pruebas de utilidades**
   
Archivo: FormatUtilsTest.kt
   - Validación del formateo de valores numéricos a formato monetario chileno.
   - Verificación de redondeo, números grandes y valor cero.

**5. Pruebas del ViewModel principal**

Archivo: MainViewModelTest.kt
   
Incluyen validación de:

   - Manejo del Drawer: apertura/cierre y actualización del elemento seleccionado.
   - Navegación interna: emisión correcta de eventos tipo PopBackStack y NavigateTo. 
   - Búsqueda de productos: actualización de texto y filtrado correcto. 
   - Carrito de compras 
     - Agregar productos 
     - Quitar solo una unidad 
     - Eliminar todas las unidades de un mismo producto 
     - Vaciar el carrito 
     - Cálculo correcto del total acumulado.