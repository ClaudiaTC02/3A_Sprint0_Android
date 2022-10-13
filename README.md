<h3 align = "center"> Sprint0_Android </h3>

---
<p align = "center"> En este repositorio se encuentra el código de la aplicación Android capaz de recibir beacons y subirlos a una base de datos
    <br>
</p>

## 📝 Tabla de contenido

- [Introducción](#Getting_started)

## 🏁 Comenzando <a name = "getting_started"> </a>

Estas instrucciones le proporcionarán una copia del proyecto en funcionamiento en su ordenador con fines de desarrollo y prueba

### Requisitos previos

Qué necesita para instalar el software y cómo instalarlo.
Lo único que se debe instalar para hacer funcionar este proyecto es XAMPP y Android Studio para ser capaces de ejecutar la aplicación en nuestro dispositivo. 

```
[XAMPP](https://www.apachefriends.org/es/download.html).
```

```
[Android Studio](https://developer.android.com/studio).
```
A su vez es si no se dispone de un servidor abierto, tendremos que dirigirnos a la ruta en la que hayamos instalado XAMPP y en httdocs crear una carpeta llamada "sprint0" con dos archivos .sql

```
conexion.sql e insertarMedida.sql
```

Dichos archivos son importantes para la correcta comunicación con la base de datos.

### Instalación

Tras haber completa la instalación de los programas, abriremos el XAMPP e iniciaremos los servicios de Apache y MySQL, también abriremos el proyecto en Android Studio

1. Dentro de la clase Logica, que se encuentra visto desde Android Studio:

```
Sprint0_Android/app/java/ctorcru.upv.sprint0android/Logica
```

2. Cambiaremos la URL, para ello necesitamos conocer nuestro ip. Para conocer la ip nos fijamos en la Ipv4 de este comando en la terminal:

```
ipconfig
```

3. A su vez necesitamos conocer el puerto por el que escucha nuestro ordenador, esto se puede ver en el XAMPP pulsado sobre config en Apache

4. Por lo tanto la URL se debería cambiar siguiendo este formato:

```
http://IP:PUERTO/sprint0/insertarMedida.php
```

5. Por último hay que concederle permisos a la aplicación de "encontrar dispositivos cercanos" activando el blueetooth (en algunos dispositivos hay que hacerlo de forma manual la concesión de permisos)