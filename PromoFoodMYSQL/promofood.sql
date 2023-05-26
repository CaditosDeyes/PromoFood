-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-05-2023 a las 05:11:11
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `promofood`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bebida`
--

CREATE TABLE `bebida` (
  `id` int(100) NOT NULL,
  `idMenuPerteneciente` int(100) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `precio` float NOT NULL,
  `ingredientes` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `bebida`
--

INSERT INTO `bebida` (`id`, `idMenuPerteneciente`, `nombre`, `precio`, `ingredientes`) VALUES
(1, 1, 'r', 2, 'r'),
(2, 2, 'Coca cola', 15, '500 ml'),
(3, 3, 'Coca cola', 15, '300 ml'),
(4, 3, 'Agua de horchata', 15, '400 ml'),
(5, 4, 's', 4, 's'),
(6, 11, 'Pepsi', 10.8, 'Espuma'),
(7, 11, 'Seven', 40.9, '1 litro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` int(100) NOT NULL,
  `idCliente` int(100) NOT NULL,
  `idRestaurantePerteneciente` int(100) NOT NULL,
  `resumen` varchar(200) NOT NULL,
  `precioIndividual` float NOT NULL,
  `solicitado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrito`
--

INSERT INTO `carrito` (`id`, `idCliente`, `idRestaurantePerteneciente`, `resumen`, `precioIndividual`, `solicitado`) VALUES
(1, 2, 2, 'Platillo: Hamburguesa chica\nPrecio: 12', 12, 1),
(2, 2, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(5, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(6, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(7, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(8, 2, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(11, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(12, 2, 4, 'Bebida: s\nPrecio: 4', 4, 1),
(13, 2, 4, 'Platillo: b\nPrecio: 9', 9, 1),
(14, 1, 2, 'Platillo: Hamburguesa chica\nPrecio: 12', 12, 1),
(15, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(16, 1, 3, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(17, 1, 3, 'Platillo: Tacos al pastor\nPrecio: 10', 10, 1),
(18, 1, 3, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(19, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(20, 1, 2, 'Platillo: Hamburguesa chica\nPrecio: 12', 12, 1),
(21, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(22, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(23, 1, 3, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(24, 1, 3, 'Bebida: Agua de horchata\nPrecio: 15', 15, 1),
(25, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(26, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(27, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30', 30, 1),
(28, 1, 2, 'Platillo: Hamburguesa chica\nPrecio: 12', 12, 1),
(29, 1, 2, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(30, 1, 3, 'Bebida: Agua de horchata\nPrecio: 15', 15, 1),
(31, 1, 3, 'Platillo: Tacos al pastor\nPrecio: 10', 10, 1),
(32, 1, 1, 'Bebida: r\nPrecio: 2', 2, 1),
(33, 1, 3, 'Bebida: Coca cola\nPrecio: 15', 15, 1),
(34, 1, 3, 'Platillo: Tacos al pastor\nPrecio: 10', 10, 1),
(35, 2, 3, 'Platillo: Tacos al pastor\nPrecio: 10', 10, 1),
(36, 2, 3, 'Bebida: Coca cola\nPrecio: 15', 15, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(100) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `correoElectronico` varchar(50) NOT NULL,
  `contrasenia` varchar(20) NOT NULL,
  `numeroTelefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `nombre`, `correoElectronico`, `contrasenia`, `numeroTelefono`) VALUES
(1, '2', '2', '2', '2'),
(2, 'Manuel', '3', '3', '3319708970');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menu`
--

CREATE TABLE `menu` (
  `idMenuPerteneciente` int(100) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `menu`
--

INSERT INTO `menu` (`idMenuPerteneciente`, `nombre`) VALUES
(1, 'tscos'),
(2, 'Hamburguesas'),
(3, 'Tacos'),
(4, 'a'),
(5, 'si'),
(6, 'hola');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id` int(100) NOT NULL,
  `idClienteSolicitado` int(100) NOT NULL,
  `idRestaurantePerteneciente` int(100) NOT NULL,
  `resumen` varchar(200) NOT NULL,
  `horaEntrega` varchar(10) NOT NULL,
  `estatus` varchar(100) NOT NULL,
  `direccionEntrega` varchar(100) NOT NULL,
  `precioFinal` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `idClienteSolicitado`, `idRestaurantePerteneciente`, `resumen`, `horaEntrega`, `estatus`, `direccionEntrega`, `precioFinal`) VALUES
(1, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30\nBebida: Coca cola\nPrecio: 15\n', '18:52', 'Entregado', '', 45),
(2, 1, 2, 'Platillo: Hamburguesa grande\nPrecio: 30\n', '13:22', 'Entregado', '', 30),
(3, 1, 2, 'Platillo: Hamburguesa chica\nPrecio: 12\n', '13:31', 'Entregado', '', 12),
(4, 1, 2, 'Bebida: Coca cola\nPrecio: 15\n', '19:20', 'Entregado', 'Real 4248A', 15),
(5, 1, 3, 'Bebida: r\nPrecio: 2\n', '20:04', 'Entregado', 'Moneda 487', 2),
(6, 1, 1, 'Bebida: Agua de horchata\nPrecio: 15\nPlatillo: Tacos al pastor\nPrecio: 10\n', '20:07', 'Entregado', '', 25),
(7, 2, 4, 'Platillo: Tacos al pastor\nPrecio: 10\nBebida: Coca cola\nPrecio: 15\n', '20:07', 'No entregado', 'Medalla', 25),
(8, 2, 3, 'Bebida: s\nPrecio: 4\nPlatillo: b\nPrecio: 9\n', '20:01', 'No entregado', '', 13),
(9, 1, 3, 'Bebida: Coca cola\nPrecio: 15\nPlatillo: Tacos al pastor\nPrecio: 10\n', '21:28', 'No entregado', 'Real 4248A', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `platillo`
--

CREATE TABLE `platillo` (
  `id` int(100) NOT NULL,
  `idMenuPerteneciente` int(100) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `precio` float NOT NULL,
  `ingredientes` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `platillo`
--

INSERT INTO `platillo` (`id`, `idMenuPerteneciente`, `nombre`, `precio`, `ingredientes`) VALUES
(1, 1, 'q', 1, 'q'),
(2, 2, 'Hamburguesa chica', 12, 'carne y queso'),
(3, 0, 'Hamburguesa grande', 30, 'doble carne'),
(4, 2, 'Hamburguesa grande', 30, 'doble carne'),
(5, 3, 'Tacos al pastor', 10, 'Carne y frijoles'),
(6, 4, 'b', 9, 'b'),
(7, 2, 'Hamburguesa de pollo', 20.5, 'Pollo grande'),
(8, 11, 'Tacos', 10.5, 'carnasa'),
(9, 11, 'Otros tacos', 15.5, 'Más carne');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resenia`
--

CREATE TABLE `resenia` (
  `id` int(100) NOT NULL,
  `idClienteSolicitado` int(100) NOT NULL,
  `idPlatillo` int(100) NOT NULL,
  `idBebida` int(100) NOT NULL,
  `nombrePlatilloBebida` varchar(100) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `puntuacion` int(5) NOT NULL,
  `estatus` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `resenia`
--

INSERT INTO `resenia` (`id`, `idClienteSolicitado`, `idPlatillo`, `idBebida`, `nombrePlatilloBebida`, `descripcion`, `puntuacion`, `estatus`) VALUES
(3, 1, 4, 0, 'Hamburguesa grande', 'Me encantó', 4, 'Calificado'),
(4, 1, 0, 2, 'Coca cola', 'wakala', 1, 'Calificado'),
(5, 1, 4, 0, 'Hamburguesa grande', 'Llegó un poco fria', 2, 'Calificado'),
(6, 1, 2, 0, 'Hamburguesa chica', 'Rico', 4, 'Calificado'),
(11, 1, 0, 3, 'Coca cola', 'Venia caliente', 2, 'Calificado'),
(13, 2, 5, 0, 'Tacos al pastor', 'algo', 0, 'Enviando'),
(14, 2, 0, 3, 'Coca cola', 'algo', 0, 'Enviando');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurante`
--

CREATE TABLE `restaurante` (
  `id` int(100) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `correoElectronico` varchar(50) NOT NULL,
  `contrasenia` varchar(20) NOT NULL,
  `domicilio` varchar(11) NOT NULL,
  `numeroTelefono` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `restaurante`
--

INSERT INTO `restaurante` (`id`, `nombre`, `correoElectronico`, `contrasenia`, `domicilio`, `numeroTelefono`) VALUES
(1, '2', '3', '3', '2', '2'),
(2, 'Mc donalds', '2', '2', 'Rio nilo', '3319708970'),
(3, 'El jimmy', '111', '111', '111', '111'),
(4, 'a', 'a', 'a', 'a', '4'),
(5, '0', '0', '0', '0', '0'),
(6, '1111', 'sjsjsjjs', '1111', 'qqqqqq', '1111'),
(7, '5', '5', '5', '5', '5'),
(8, '6', '6', '6', '6', '6'),
(9, '1', '1', '1', '1', '1'),
(10, '3', '3', '3', '3', '3'),
(11, '1', '1', '1', '1', '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bebida`
--
ALTER TABLE `bebida`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`idMenuPerteneciente`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `platillo`
--
ALTER TABLE `platillo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `resenia`
--
ALTER TABLE `resenia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `restaurante`
--
ALTER TABLE `restaurante`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `bebida`
--
ALTER TABLE `bebida`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `menu`
--
ALTER TABLE `menu`
  MODIFY `idMenuPerteneciente` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `platillo`
--
ALTER TABLE `platillo`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `resenia`
--
ALTER TABLE `resenia`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `restaurante`
--
ALTER TABLE `restaurante`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
