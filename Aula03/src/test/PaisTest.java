package test;

import static org.junit.Assert.assertEquals;
import model.Pais;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import service.PaisService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaisTest {
	Pais pais, copia, paises[];
	PaisService paisService;
	static int id = 0;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais();
		pais.setId(id);
		pais.setNome("Italia");
		pais.setPopulacao(228406639);
		pais.setArea(5834000.00);
		copia = new Pais();
		copia.setId(id);
		copia.setNome("Italia");
		copia.setPopulacao(228406639);
		copia.setArea(5834000.00);
		paisService = new PaisService();
		System.out.println(pais);
		System.out.println(copia);
		System.out.println(id);
	}
	
	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		Pais fixture = new Pais();
		fixture.setId(1);
		fixture.setNome("Brasil");
		fixture.setPopulacao(213053455);
		fixture.setArea(8516000.00);
		PaisService novoService = new PaisService();
		Pais novo = novoService.carregar(1);
		assertEquals("testa inclusao", novo, fixture);
	}

	@Test
	public void test01Criar() {
		System.out.println("criar");
		id = paisService.criar(pais);
		System.out.println(id);
		copia.setId(id);
		assertEquals("testa criacao", pais, copia);
	}

	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setNome("194564132");
		copia.setNome("194564132");
		paisService.atualizar(pais);
		pais = paisService.carregar(pais.getId());
		assertEquals("testa atualizacao", pais, copia);
	}

	@Test
	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome(null);
		copia.setPopulacao(0);
		copia.setArea(0);
		paisService.excluir(id);
		pais = paisService.carregar(id);
		assertEquals("testa exclusao", pais, copia);
	}
	
	@Test
	public void test04Maior() {
		System.out.println("maior");//apenas informa no display
		Pais fixture = new Pais();
		fixture.setId(3);
		fixture.setNome("China");
		fixture.setPopulacao(1395553739);
		fixture.setArea(9597000.00);
		
		PaisService novoService = new PaisService();
		int id = novoService.maior(paises);
		
		Pais newPais = novoService.carregar(id);
		assertEquals("testa maiorPopulação", newPais, fixture);
	}
	
	@Test 
	public void test05menor() { 
		System.out.println("menor");//apenas informa no display
		Pais fixture = new Pais();
		fixture.setId(5);
		fixture.setNome("Portugal");
		fixture.setPopulacao(10227660);
		fixture.setArea(92212.00);
		
		PaisService novoService = new PaisService();
		int id = novoService.menor(paises);
		
		Pais novoPais = novoService.carregar(id);
		assertEquals("testa menorArea", novoPais, fixture);
	}
	
	
}