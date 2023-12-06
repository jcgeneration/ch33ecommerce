package org.generation.ecommerce;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.generation.ecommerce.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;


@SpringBootTest
@AutoConfigureMockMvc
class EcommerceApplicationTests {
	@Autowired
	private MockMvc mockMvc; 
	
	@Test
	@DisplayName("Se prueba el endpoint http://localhost:8080/api/productos/1")
	void pruebaGET() throws Exception {
		this.mockMvc.perform( get("/api/productos/1") )
							.andDo( print() )
							.andExpect(status().isOk()) 
							.andExpect( 
									content().string(containsString("Plumas Bic Cristal Intenso"))  
									);
	}//pruebaGET	
	
	@Test
	@Disabled("Deshabilitado una vez que ha sido probado")
	@DisplayName("Se borra el producto en el endpoint http://localhost:8080/api/productos/7")
	void pruebaDELETE() throws Exception {
		this.mockMvc.perform( delete("/api/productos/7") )
		.andDo( print() )
		.andExpect(status().isOk()) 
		.andExpect( content().string( 
				containsString("Cuaderno Profesional Scribe / Cuadro chico / 100 hojas")
				)  );
	}//pruebaDELETE	
	@Test
	@Disabled("Ya se probó")
	@DisplayName("Se agrega un nuevo producto")
	void pruebaPOST()throws Exception {
		Producto p = new Producto();
		p.setNombre("Cuaderno Profesional Scribe / raya / 100 hojas"); 
		p.setImagen("producto_4.jpg");
		p.setDescripcion("Cuaderno Profesional Scribe / raya / 100 hojas"); 
		p.setPrecio(47.50);
		this.mockMvc.perform( post("/api/productos/")
				.contentType(MediaType.APPLICATION_JSON)
				//.header("Authorization", "Bearer: Token")
				.content(asJsonString(p)  )  )
		.andDo(print())
		.andExpect(status().isOk() )
		.andExpect(
				content().string(
						containsString("Cuaderno Profesional Scribe / raya / 100 hojas") )
				);
	}//pruebaPOST
	
	private static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }//catch
	 } // asJsonString
	
	@Test
	//@Disabled("Update Deshabilitado")
	@DisplayName("Se modifica el producto 9 con un nuevo precio")
	void pruebaPUT() throws Exception{
		this.mockMvc.perform( put("/api/productos/9")
				.queryParam("precio", "52.20")			)
		.andDo(print())
		.andExpect(status().isOk() )
		.andExpect(
				content().string(
						containsString("52.2") )
				);
	}//pruebaPUT
	
	
	
	
	
}//class EcommerceApplicationTests
