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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.controledegastos.api.dtos.TipoDto;
import com.mila.controledegastos.api.entities.Tipo;
import com.mila.controledegastos.api.response.Response;
import com.mila.controledegastos.api.services.TipoService;

@RestController
@RequestMapping("/api/tipo")

public class TipoController {

	private static final Logger log = LoggerFactory.getLogger(TipoController.class);

	@Autowired
	private TipoService tipoService;

	public TipoController() {
	}

	@GetMapping(value = "olatipo/{envio}")
	public ResponseEntity<String> ola(@PathVariable("envio") String envio) throws ParseException {

		System.out.println(envio);
		return ResponseEntity.ok("Ola teste tipo da Camila");
	}

	/**
	 * Adiciona um novo tipo.
	 * 
	 * @param tipo
	 * @param result
	 * @return ResponseEntity<Response<TipoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TipoDto>> adicionar(@Valid @RequestBody TipoDto tipoDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando tipo: {}", tipoDto.toString());
		Response<TipoDto> response = new Response<TipoDto>();
		validarDadosExistentes(tipoDto, result);
		Tipo tipo = this.converterTipoParaDto(tipoDto);

		if (result.hasErrors()) {
			log.error("Erro validando tipo: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		tipo = this.tipoService.persistir(tipo);
		response.setData(this.converterDtoParaTipo(tipo));
		return ResponseEntity.ok(response);
	}

	/**
	 * Verifica se tipo já está cadastrado.
	 * 
	 * @param tipoDto
	 * @param result
	 */
	private void validarDadosExistentes(TipoDto tipoDto, BindingResult result) {
		this.tipoService.buscarPorTipo(tipoDto.getTipo())
				.ifPresent(tipo -> result.addError(new ObjectError("tipo", "Já existente.")));
	}

	/**
	 * Converte os dados do DTO para tipo.
	 * 
	 * @param tipoDto
	 * @param result
	 * @return Tipo
	 * @throws NoSuchAlgorithmException
	 */
	private Tipo converterTipoParaDto(TipoDto tipoDto) {
		Tipo tipo = new Tipo();
		tipo.setTipo(tipoDto.getTipo());

		return tipo;
	}

	/**
	 * Popula o DTO de tipo com os dados do tipo.
	 * 
	 * @param Tipo
	 * @return TipoDto
	 */
	private TipoDto converterDtoParaTipo(Tipo tipo) {
		TipoDto tipoDto = new TipoDto();
		tipoDto.setId(tipo.getId());
		tipoDto.setTipo(tipo.getTipo());

		return tipoDto;
	}

	/**
	 * Remove um tipo por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Tipo>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo tipo: {}", id);
		Response<String> response = new Response<String>();
		Optional<Tipo> tipo = this.tipoService.buscarPorId(id);

		if (!tipo.isPresent()) {
			log.info("Erro ao remover devido ao tipo ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover tipo. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.tipoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Atualiza os dados de um tipo.
	 * 
	 * @param id
	 * @param tipoDto
	 * @param result
	 * @return ResponseEntity<Response<tipoDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TipoDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody TipoDto tipoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando tipo: {}", tipoDto.toString());
		Response<TipoDto> response = new Response<TipoDto>();

		Optional<Tipo> tipo = this.tipoService.buscarPorId(id);

		if (!tipo.isPresent()) {
			log.info("Erro ao atualizar devido ao tipo ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao atualizar tipo. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosTipo(tipo.get(), tipoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando tipo: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.tipoService.persistir(tipo.get());
		response.setData(this.converterDtoParaTipo(tipo.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do tipo com base nos dados encontrados no DTO.
	 * 
	 * @param tipo
	 * @param tipoDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosTipo(Tipo tipo, TipoDto tipoDto, BindingResult result) throws NoSuchAlgorithmException {

		if (!tipo.getTipo().equals(tipoDto.getTipo())) {
			this.tipoService.buscarPorTipo(tipoDto.getTipo())
					.ifPresent(func -> result.addError(new ObjectError("Tipo", "Tipo já existente.")));
			tipo.setTipo(tipoDto.getTipo());
		}
	}

	/**
	 * Retorna um tipo dado um Id.
	 * 
	 * @param Id
	 * @return ResponseEntity<Response<TipoDto>>
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<TipoDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando tipo por Id: {}", id);
		Response<TipoDto> response = new Response<TipoDto>();
		Optional<Tipo> tipo = this.tipoService.buscarPorId(id);

		if (!tipo.isPresent()) {
			log.error("Tipo não encontrado para o Id: {}", id);
			response.getErrors().add("Tipo não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterDtoParaTipo(tipo.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna todos os registros tipo.
	 * 
	 * @return ResponseEntity<Response<TipoDto>>
	 */
	@GetMapping(value = "all")
	public ResponseEntity<Response<List<TipoDto>>> buscarPorTodosTipo() {
		log.info("Buscando todos os registros tipo");
		Response<List<TipoDto>> response = new Response<>();
		Optional<List<Tipo>> tipoList = this.tipoService.buscarPorTodosTipo();

		if (!tipoList.isPresent()) {
			log.error("Não existe registros tipo");
			response.getErrors().add("Não existe registros tipo");
			return ResponseEntity.badRequest().body(response);
		}

		List<TipoDto> listTipoDto = new ArrayList<>();
		for (Tipo tipo : tipoList.get()) {
			listTipoDto.add(this.converterDtoParaTipo(tipo));
		}
		response.setData(listTipoDto);
		return ResponseEntity.ok(response);

	}

}
