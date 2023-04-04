package com.aut.prueba.model;

import com.aut.prueba.security.enums.RolNombre;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;

	public Rol(@NotNull RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}

}
