-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-06-2025 a las 21:09:06
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gpps2_0`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividad`
--

CREATE TABLE `actividad` (
  `id` int(11) NOT NULL,
  `nombre_actividad` varchar(255) NOT NULL,
  `horas` int(11) NOT NULL,
  `propuesta_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `actividad`
--

INSERT INTO `actividad` (`id`, `nombre_actividad`, `horas`, `propuesta_id`) VALUES
(1, 'Relevamiento de requisitos', 100, 1),
(2, 'Diseño de interfaz', 8, 1),
(3, 'Desarrollo backend', 12, 1),
(4, 'Pruebas y documentación', 10, 1),
(5, 'Análisis de reportes existentes', 60, 2),
(6, 'Desarrollo de scripts', 40, 2),
(7, 'Automatización y pruebas', 8, 2),
(8, 'Instalación de Odoo', 90, 3),
(9, 'Configuración de módulos', 10, 3),
(10, 'Capacitación a usuarios', 6, 3),
(11, 'Soporte técnico', 4, 3),
(12, 'Diseño de UI', 70, 4),
(13, 'Integración con DB', 90, 4),
(14, 'Pruebas funcionales', 6, 4),
(15, 'Despliegue en dispositivos', 5, 4),
(16, 'Diseño de sistema de encuestas', 70, 5),
(17, 'Desarrollo del panel de administración', 90, 5),
(18, 'Módulo de resultados', 6, 5),
(19, 'Escaneo de redes', 50, 6),
(20, 'Pruebas de penetración', 77, 6),
(21, 'Informe de vulnerabilidades', 6, 6),
(22, 'Presentación de resultados', 4, 6),
(23, 'Diseño base de datos', 60, 7),
(24, 'Desarrollo sistema reservas', 90, 7),
(25, 'Desarrollo front-end', 2, 7),
(26, 'Validaciones y pruebas', 5, 7),
(27, 'Implementación', 4, 7),
(28, 'Instalación de Prometheus', 50, 8),
(29, 'Configuración de métricas', 60, 8),
(30, 'Integración con Grafana', 5, 8),
(31, 'Monitoreo real', 6, 8),
(32, 'Diseño de flujo conversacional', 50, 9),
(33, 'Entrenamiento del modelo', 50, 9),
(34, 'Integración en la web', 6, 9),
(35, 'Evaluación de respuestas', 5, 9),
(36, 'Digitalización de documentos', 60, 10),
(37, 'Diseño de estructura de carpetas', 50, 10),
(38, 'Desarrollo del sistema', 8, 10),
(39, 'Carga inicial y pruebas', 6, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`dni`, `nombre`, `apellido`) VALUES
('10000001', 'facundo', 'Pérez'),
('10000002', 'bruno', 'Gómez'),
('10000003', 'andres', 'López'),
('10000004', 'jose', 'Fernández'),
('10000005', 'mauro', 'Martínez'),
('11111111', 'institucion', 'int');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propuesta`
--

CREATE TABLE `propuesta` (
  `id` int(11) NOT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `area_interes` varchar(255) DEFAULT NULL,
  `objetivo` text DEFAULT NULL,
  `comentarios` text DEFAULT NULL,
  `aceptada` tinyint(4) DEFAULT NULL,
  `creador` varchar(50) DEFAULT NULL,
  `alumno` varchar(50) DEFAULT NULL,
  `tutor` varchar(50) DEFAULT NULL,
  `profesor` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `propuesta`
--

INSERT INTO `propuesta` (`id`, `titulo`, `descripcion`, `area_interes`, `objetivo`, `comentarios`, `aceptada`, `creador`, `alumno`, `tutor`, `profesor`) VALUES
(1, 'Desarrollo de sistema de turnos médicos', 'Implementación de una app web para gestión de turnos médicos en una clínica local.', 'Desarrollo Web', 'Aplicar conocimientos de back y frontend', NULL, NULL, 'alumno01', 'alumno01', 'tutor01', 'profesor01'),
(2, 'Automatización de informes en Excel con Python', 'Generación automática de reportes de ventas usando scripts de Python en una pyme.', 'Automatización', 'Incorporar scripting en entornos reales', NULL, NULL, 'admin01', 'alumno01', 'tutor01', 'profesor01'),
(3, 'Implementación de ERP Libre', 'Personalización e instalación de un ERP libre (Odoo) para una cooperativa.', 'Sistemas empresariales', 'Comprender flujos de trabajo empresariales', NULL, NULL, 'tutor01', 'alumno01', 'tutor01', 'profesor01'),
(4, 'App móvil para gestión de stock en almacenes', 'Desarrollar una app Android para control de inventario con escaneo de códigos.', 'Aplicaciones móviles', 'Mejorar habilidades móviles con bases de datos', NULL, NULL, 'profesor01', 'alumno01', 'tutor01', 'profesor01'),
(5, 'Sistema de encuestas en línea', 'Crear un sistema para diseñar y responder encuestas con análisis estadístico.', 'Desarrollo Web', 'Desarrollar sistemas dinámicos con análisis de datos', NULL, NULL, 'director01', 'alumno01', 'tutor01', 'profesor01'),
(6, 'Análisis de vulnerabilidades en redes WiFi', 'Auditoría de seguridad sobre redes inalámbricas en la universidad.', 'Ciberseguridad', 'Aplicar herramientas de auditoría de red', NULL, NULL, 'alumno01', 'alumno01', 'tutor01', 'profesor01'),
(7, 'Implementación de sistema de reservas deportivas', 'Crear una aplicación para reservar turnos en canchas de fútbol, paddle y tenis.', 'Desarrollo Web y Mobile', 'Integrar sistemas y base de datos en app real', NULL, NULL, 'admin01', 'alumno01', 'tutor01', 'profesor01'),
(8, 'Panel de monitoreo de servidores', 'Visualización en tiempo real de métricas de servidores usando Grafana y Prometheus.', 'Infraestructura IT', 'Aprender monitoreo de infraestructuras reales', NULL, NULL, 'tutor01', 'alumno01', 'tutor01', 'profesor01'),
(9, 'Desarrollo de chatbot institucional', 'Creación de un chatbot para atención al estudiante con IA básica.', 'Inteligencia Artificial', 'Aplicar procesamiento de lenguaje natural', NULL, NULL, 'profesor01', 'alumno01', 'tutor01', 'profesor01'),
(10, 'Gestión documental digital', 'Sistema para digitalizar y organizar documentos internos.', 'Transformación digital', 'Mejorar procesos administrativos mediante software', NULL, NULL, 'director01', 'alumno01', 'tutor01', 'profesor01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`, `descripcion`) VALUES
(1, 'alumno', 'alumno que quiere realizar la PPS'),
(2, 'administrador', 'admin'),
(3, 'tutor', 'encargado de evaluar al alumno por parte de la institución'),
(4, 'institución', 'institución encargada de proponer una PPS'),
(5, 'profesor', 'acompaña y evalúa al alumno'),
(6, 'director de carrera', 'evalúa las propuestas de PPS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `usuario` varchar(50) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `rol` int(11) DEFAULT NULL,
  `persona` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`usuario`, `contrasena`, `email`, `activo`, `rol`, `persona`) VALUES
('andres', 'admin123', 'andres.gomez@email.com', 1, 2, '10000002'),
('bruno', 'bruno123', 'bruno.lopez@email.com', 1, 3, '10000003'),
('jose01', 'jose123', 'jose.fernandez@email.com', 1, 5, '10000004'),
('mauro01', 'dir123', 'diego.martinez@email.com', 1, 6, '10000005'),
('Rulo', 'clave123', 'rulo.perez@email.com', 1, 1, '10000001'),
('UNLP01', 'inst123', 'UNLP@email.com', 1, 4, '11111111');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `propuesta_id` (`propuesta_id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `propuesta`
--
ALTER TABLE `propuesta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `creador` (`creador`),
  ADD KEY `alumno` (`alumno`),
  ADD KEY `tutor` (`tutor`),
  ADD KEY `profesor` (`profesor`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuario`),
  ADD KEY `rol` (`rol`),
  ADD KEY `persona` (`persona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `actividad`
--
ALTER TABLE `actividad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `propuesta`
--
ALTER TABLE `propuesta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD CONSTRAINT `actividad_ibfk_1` FOREIGN KEY (`propuesta_id`) REFERENCES `propuesta` (`id`);

--
-- Filtros para la tabla `propuesta`
--
ALTER TABLE `propuesta`
  ADD CONSTRAINT `propuesta_ibfk_1` FOREIGN KEY (`creador`) REFERENCES `usuario` (`usuario`),
  ADD CONSTRAINT `propuesta_ibfk_2` FOREIGN KEY (`alumno`) REFERENCES `usuario` (`usuario`),
  ADD CONSTRAINT `propuesta_ibfk_3` FOREIGN KEY (`tutor`) REFERENCES `usuario` (`usuario`),
  ADD CONSTRAINT `propuesta_ibfk_4` FOREIGN KEY (`profesor`) REFERENCES `usuario` (`usuario`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`persona`) REFERENCES `persona` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
