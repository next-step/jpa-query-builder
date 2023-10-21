package persistence.sql.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "fixture")
public class FixtureClass {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Column(unique = true)
	private String uniqueColumn;

	@Column(nullable = false)
	private String notNullColumn;


	@Column(unique = true, nullable = false)
	private String uniqueNotNullColumn;

	@Transient
	private Integer transientColumn;
}
