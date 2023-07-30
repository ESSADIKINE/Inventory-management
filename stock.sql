-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 30, 2023 at 03:28 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stock`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_preter`
--

CREATE TABLE `tb_preter` (
  `id_Preter` int(11) NOT NULL,
  `nom` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_tel` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_Produit` int(11) NOT NULL,
  `quantité` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_preter`
--

INSERT INTO `tb_preter` (`id_Preter`, `nom`, `no_tel`, `id_Produit`, `quantité`) VALUES
(3, 'Essadikine', '0661626364', 2, 20),
(4, 'Prestataire 1', '0666666666', 5, 10),
(5, 'Prestataire', '0678912345', 3, 60),
(6, 'Prestataire', '0666555444', 7, 40),
(7, 'Prestataire', '0666555444', 4, 30);

-- --------------------------------------------------------

--
-- Table structure for table `tb_produit`
--

CREATE TABLE `tb_produit` (
  `id_Produit` int(11) NOT NULL,
  `nom` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `quantité` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_produit`
--

INSERT INTO `tb_produit` (`id_Produit`, `nom`, `quantité`) VALUES
(2, 'laptop', 20),
(3, 'clé', 100),
(4, 'clavier', 30),
(5, 'chaise', 15),
(7, 'souris', 40),
(8, 'Camera', 20),
(9, 'Stylo', 150),
(10, 'Cable', 60);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_preter`
--
ALTER TABLE `tb_preter`
  ADD PRIMARY KEY (`id_Preter`);

--
-- Indexes for table `tb_produit`
--
ALTER TABLE `tb_produit`
  ADD PRIMARY KEY (`id_Produit`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_preter`
--
ALTER TABLE `tb_preter`
  MODIFY `id_Preter` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tb_produit`
--
ALTER TABLE `tb_produit`
  MODIFY `id_Produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
