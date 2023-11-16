package org.hibernate.orm.test.query.hql;

import org.hibernate.orm.test.query.sqm.domain.Person;
import org.hibernate.query.SelectionQuery;
import org.hibernate.testing.orm.junit.BaseSessionFactoryFunctionalTest;
import org.hibernate.testing.orm.junit.JiraKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@JiraKey(value = "HHH-17416")
public class HHH17416Test extends BaseSessionFactoryFunctionalTest {

	private static final Person person = new Person();
	static {
		person.setPk(7);
		person.setNickName("Tadpole");
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {Person.class};
	}

	@BeforeEach
	public void setup() {
		inTransaction(session -> session.persist(person));
	}

	@AfterEach
	public void teardown() {
		inTransaction(session -> session.createMutationQuery("delete from Person").executeUpdate());
	}

	@Test
	public void testWhereClauseWithTuple() {
		sessionFactoryScope().inTransaction(
				entityManager -> {
					SelectionQuery<Person> selectionQuery = entityManager.createSelectionQuery("from Person p where (p.id, p.nickName) = (:val1, :val2)", Person.class);
					selectionQuery = selectionQuery.setParameter("val1", person.getPk(), Integer.class).setParameter("val2", person.getNickName(), String.class);
					Person retrievedPerson = selectionQuery.getSingleResult();
					Assertions.assertEquals(person.getPk(), retrievedPerson.getPk());
					Assertions.assertEquals(person.getNickName(), retrievedPerson.getNickName());
				}
		);
	}
}
