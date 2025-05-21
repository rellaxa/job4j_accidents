package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accident")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	private String name;

	private String text;

	private String address;

	@ManyToOne
	@JoinColumn(name = "accident_type_id")
	private AccidentType type;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@ToString.Include
	@JoinTable(
			name = "accident_article",
			joinColumns = { @JoinColumn(name = "accident_id") },
			inverseJoinColumns = { @JoinColumn(name = "article_id")}
	)
	private Set<Article> articles;
}
