## vraptor-test

Possui a mesma idéia do vraptor-test da caelum, porém subindo um tomcat embutido, pois tive problemas ao usar o jetty com o maven

# instalação

Como esta dependência não se encontra no repositório maven, você tem que baixar o projeto para sua máquina e dar um mvn install.
No pom fica

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vraptor-test</artifactId>
			<version>1.0.1</version>
			<scope>test</scope>
		</dependency>
		
# uso

Crie uma classe que irá ser extendida: 

	public class EmbeddedServer {
		protected final Vraptor VRAPTOR = new VraptorTomcat(new File("src/main/webapp"), 9080);

		@Before
		public void initServer() throws LifecycleException{
			VRAPTOR.start();
		}

		@After
		public void stopServer() throws LifecycleException{
			VRAPTOR.stop();
		}

	}
	
Na sua classe de teste

	import static br.com.caelum.restfulie.contenttype.ContentTypes.form;
	import static br.com.caelum.vraptor.test.http.parameter.FormEncodedTransformer.paramsFor;

	public class ClienteControllerIntegrationTest extends EmbeddedServer{

		@Test
		public void deveria_me_levar_para_pagina_de_cadastro_de_cliente(){

			Response resposta = VRAPTOR.at("/cliente/novo").get();

			Assert.assertEquals(200, resposta.getCode());

			String conteudoResposta = resposta.getContent();

			Assert.assertTrue(conteudoResposta.contains("Não informado"));
			Assert.assertTrue(conteudoResposta.contains("Masculino"));
			Assert.assertTrue(conteudoResposta.contains("Feminino"));

		}

		@Test
		public void deveria_cadastrar_um_novo_cliente() throws IOException, IllegalArgumentException, IllegalAccessException{

			Cliente cliente = new Cliente();
			cliente.setNome("Lucas Medeiros");

			Response response = VRAPTOR.at("/cliente").as(form()).post(paramsFor(cliente));
			Assert.assertEquals(200, response.getCode());
			Assert.assertTrue(response.getContent().contains("Lucas Medeiros"));

		}
	}
	
A classe ContentTypes possui alguns mime types: json() (application/json), xml() (application/xml) e form() (application/x-www-form-urlencoded).
O restfulie faz requisições com content type application/x-www-form-urlencoded, porém os parâmetros a serem recebidos são Map<String, String>,
onde a chave é o name do input e o valor é o próprio valor informado no input.
O método paramsFor recebe a sua classe que você usa para fazer o bind dos inputs e já a converte em um Map<String, String> =P



