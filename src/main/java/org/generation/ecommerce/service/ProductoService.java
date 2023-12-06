package org.generation.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.generation.ecommerce.model.Producto;
import org.generation.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductoService {
	
	private final ProductoRepository productoRepository;
	@Autowired
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}//constructor

	public List<Producto> getAllProductos() {
		return productoRepository.findAll() ;
	}//getAllProductos
	
	public Producto getProducto(long id) {
		return productoRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("El Producto con el id [" + id
						+ "] no existe")
			  );
	}//getProducto
	
	public Producto deleteProducto(long id) {
		Producto prod=null;
		if (productoRepository.existsById(id)) {
			prod = productoRepository.findById(id).get();
			productoRepository.deleteById(id);
		}//if existById
		return prod;
	}//deleteProducto
	
	public Producto addProducto(Producto producto) {
		Optional<Producto> tmpProd = productoRepository.findByNombre(producto.getNombre());
		if (tmpProd.isEmpty()) { // no se encuentra el producto con ese nombre
			return productoRepository.save(producto);
		} else {
			System.out.println("Ya existe el producto con el nombre [" + 
						producto.getNombre() + "]");
			return null;
		}//else 
	}//addProducto
	
	public Producto updateProducto(long id, String nombre, 
					String descripcion, String imagen, Double precio) {
		Producto prod=null;
		if (productoRepository.existsById(id)) {
			prod = productoRepository.findById(id).get();
			if(nombre!= null) prod.setNombre(nombre);
			if(descripcion!= null) prod.setDescripcion(descripcion);
			if(imagen!= null) prod.setImagen(imagen);
			if(precio!= null) prod.setPrecio(precio);
			productoRepository.save(prod);
		}//existById
		return prod;
	}//updateProducto
	
	
}//class ProductoService
