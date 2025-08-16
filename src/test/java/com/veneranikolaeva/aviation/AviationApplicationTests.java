package com.veneranikolaeva.aviation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AviationApplicationTests {

	@Autowired MockMvc mvc;
	@Autowired ObjectMapper om;

	static void h2Props(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url",
				() -> "jdbc:h2:mem:aviation;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		r.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
		r.add("spring.datasource.username", () -> "sa");
		r.add("spring.datasource.password", () -> "");
		r.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.H2Dialect");
		r.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");  // creates tables
		r.add("spring.sql.init.mode", () -> "never");  // ignore schema.sql/data.sql
		r.add("spring.flyway.enabled", () -> "false");  // if Flyway on classpath
		r.add("spring.liquibase.enabled", () -> "false");  // if Liquibase on classpath
	}

	// --- helpers ---

	private long createCity(String name) throws Exception {
		String body = "{\"name\":\"" + name + "\",\"cityName\":\"" + name + "\"}";
		MvcResult res = mvc.perform(
						post("/api/cities")
								.contentType(MediaType.APPLICATION_JSON)
								.content(body))
				.andReturn();

		int status = res.getResponse().getStatus();
		assertTrue(status == 200 || status == 201,
				"Expected 200 or 201 from POST /api/cities, got " + status);

		JsonNode json = om.readTree(res.getResponse().getContentAsByteArray());
		return json.get("id").asLong();
	}

	// --- tests ----

	@Test
	void getCityById_roundTrip() throws Exception {
		long id = createCity("Toronto");

		mvc.perform(get("/api/cities/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value("Toronto"));
	}

	@Test
	void listCities_hasAtLeastTwo() throws Exception {
		createCity("Toronto");
		createCity("Montreal");

		MvcResult res = mvc.perform(get("/api/cities"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andReturn();

		JsonNode arr = om.readTree(res.getResponse().getContentAsByteArray());
		assertTrue(arr.isArray() && arr.size() >= 2,
				"Expected at least 2 cities, got: " + arr.size());
	}

	@Test
	void getCity_missing_returns404() throws Exception {
		mvc.perform(get("/api/cities/{id}", 999_999))
				.andExpect(status().isNotFound());
	}
}

