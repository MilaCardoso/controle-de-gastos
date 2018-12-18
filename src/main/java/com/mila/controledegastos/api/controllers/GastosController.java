package com.mila.controledegastos.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.controledegastos.api.dtos.GastosDto;
import com.mila.controledegastos.api.entities.Gastos;
import com.mila.controledegastos.api.response.Response;
import com.mila.controledegastos.api.services.GastosService;

@RestController
@RequestMapping("/api/gastos")

public class GastosController {

	private static final Logger log = LoggerFactory.getLogger(GastosController.class);

	@Autowired
	private GastosService gastosService;

	public GastosController() {
	}

	@GetMapping(value = "ola/{envio}")
	public ResponseEntity<String> ola(@PathVariable("envio")  String envio)
			throws ParseException {
		
		System.out.println(envio);
		return ResponseEntity.ok("Ola Camila");
	}
	
	/**
	 * Adiciona um novo gasto.
	 * 
	 * @param gastos
	 * @param result
	 * @return ResponseEntity<Response<GastosDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<GastosDto>> adicionar(@Valid @RequestBody GastosDto gastosDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando gasto: {}", gastosDto.toString());
		Response<GastosDto> response = new Response<GastosDto>();
		Gastos gastos = this.converterGastosParaDto(gastosDto);

		if (result.hasErrors()) {
			log.error("Erro validando gasto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		gastos = this.gastosService.persistir(gastos);
		response.setData(this.converterDtoParaGastos(gastos));
		return ResponseEntity.ok(response);
	}

	/**
	 * Converte os dados do DTO para gastos.
	 * 
	 * @param gastosDto
	 * @param result
	 * @return Gastos
	 * @throws NoSuchAlgorithmException
	 */
	private Gastos converterGastosParaDto(GastosDto gastosDto) {
		Gastos gastos = new Gastos();
		gastos.setData(gastosDto.getData());
		gastos.setDescricao(gastosDto.getDescricao());
		gastos.setValor(gastosDto.getValor());
		gastos.setTipo(gastosDto.getTipo());

		return gastos;
	}

	/**
	 * Popula o DTO de gastos com os dados do gastos.
	 * 
	 * @param Gastos
	 * @return GastosDto
	 */
	private GastosDto converterDtoParaGastos(Gastos gastos) {
		GastosDto gastosDto = new GastosDto();
		gastosDto.setId(gastos.getId());
		gastosDto.setData(gastos.getData());
		gastosDto.setDescricao(gastos.getDescricao());
		gastosDto.setValor(gastos.getValor());
		gastosDto.setTipo(gastos.getTipo());

		return gastosDto;
	}

	/**
	 * Remove um gasto por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Gastos>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo gasto: {}", id);
		Response<String> response = new Response<String>();
		Optional<Gastos> gastos = this.gastosService.buscarPorId(id);

		if (!gastos.isPresent()) {
			log.info("Erro ao remover devido ao tipo ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover gasto. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.gastosService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Atualiza os dados de um gasto.
	 * 
	 * @param id
	 * @param gastosDto
	 * @param result
	 * @return ResponseEntity<Response<gastosDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<GastosDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody GastosDto gastosDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando gasto: {}", gastosDto.toString());
		Response<GastosDto> response = new Response<GastosDto>();

		Optional<Gastos> gastos = this.gastosService.buscarPorId(id);

		if (!gastos.isPresent()) {
			log.info("Erro ao atualizar devido ao gasto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao atualizar gasto. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosGastos(gastos.get(), gastosDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando gasto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.gastosService.persistir(gastos.get());
		response.setData(this.converterDtoParaGastos(gastos.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do gastos com base nos dados encontrados no DTO.
	 * 
	 * @param gastos
	 * @param gastosDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 * 
	 * PERGUNTAR FELIPE
	 * 
	 */ 
	private void atualizarDadosGastos(Gastos gastos, GastosDto gastosDto, BindingResult result)
			throws NoSuchAlgorithmException {
		gastos.setData(gastosDto.getData());
		gastos.setDescricao(gastosDto.getDescricao());
		gastos.setValor(gastosDto.getValor());
		gastos.setTipo(gastosDto.getTipo());		
	}	
	
	/**
	 * Retorna um gasto dado um Id.
	 * 
	 * @param Id
	 * @return ResponseEntity<Response<GastosDto>>
	 * 
	 * VER DATA COM FELIPE
	 * 
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<GastosDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando gasto por Id: {}", id);
		Response<GastosDto> response = new Response<GastosDto>();
		Optional<Gastos> gastos = this.gastosService.buscarPorId(id);

		if (!gastos.isPresent()) {
			log.error("Gasto não encontrado para o Id: {}", id);
			response.getErrors().add("Gasto não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterDtoParaGastos(gastos.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna todos os registros gastos.
	 * 
	 * @return ResponseEntity<Response<GastosDto>>
	 */
	@GetMapping(value = "all")
	public ResponseEntity<Response<List<GastosDto>>> buscarPorTodosGastos() {
		log.info("Buscando todos os registros gastos");
		Response<List<GastosDto>> response = new Response<>();
		Optional<List<Gastos>> gastosList = this.gastosService.buscarPorTodosGastos();

		if (!gastosList.isPresent()) {
			log.error("Não existe registros gastos");
			response.getErrors().add("Não existe registros gastos");
			return ResponseEntity.badRequest().body(response);
		}

		List<GastosDto> listGastosDto = new ArrayList<>();
		for (Gastos gastos : gastosList.get()) {
			listGastosDto.add(this.converterDtoParaGastos(gastos));
		}
		response.setData(listGastosDto);
		return ResponseEntity.ok(response);

	}


}
