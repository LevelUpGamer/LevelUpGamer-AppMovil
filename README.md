# Level Up Gamer - Aplicación Móvil

## Descripción del Proyecto
Level Up Gamer es una aplicación móvil desarrollada en Kotlin utilizando Android Studio y el patrón MVVM.  
Permite a los usuarios navegar por un catálogo de productos gamer, agregar artículos al carrito de compras y gestionar su sesión.  
El objetivo del proyecto es demostrar el uso de componentes de Jetpack Compose, navegación, persistencia de datos y manejo de estado en Android.

## Integrantes
- Aran Opazo 
- Isabel Sánchez

## Funcionalidades Implementadas
- **Inicio de sesión local:** validación básica de usuario y acceso a la aplicación.
- **Menú principal con Drawer:** navegación entre las secciones Home, Catálogo, Carrito y Cerrar sesión.
- **Catálogo de productos:** lista dinámica con imágenes, nombres y precios.
- **Carrito de compras:** permite agregar y eliminar productos.
- **Persistencia de datos temporal:** manejo de ítems seleccionados mediante `CartManager` y `ViewModel`.
- **Arquitectura y organización:** 
  - `model.navigation` con `Screen` y `NavigationEvent`.
  - Separación por paquetes (`adapter`, `data`, `utils`, `viewmodel`, `ui/theme`).