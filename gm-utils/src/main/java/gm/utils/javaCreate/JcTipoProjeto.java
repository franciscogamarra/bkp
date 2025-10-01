package gm.utils.javaCreate;

import gm.utils.exception.NaoImplementadoException;

public abstract class JcTipoProjeto {

	protected abstract JcAnotacao getStateless();

	protected JcAnotacao getApi() {
		throw new NaoImplementadoException();
	}

	protected JcAnotacao getCdi() {
		throw new NaoImplementadoException();
	}

	public JcAnotacao postConstruct() {
		return new JcAnotacao("javax.annotation.PostConstruct");
	}

	public JcAnotacao pathVariable() {
		throw new NaoImplementadoException();
	}
	
	public JcAnotacao getRequestBodyAnnotation() {
		throw new NaoImplementadoException();
	}

	public JcAnotacao getRequestHeaderAnnotation() {
		throw new NaoImplementadoException();
	}

	public JcAnotacao getMapping() {
		throw new NaoImplementadoException();
	}

	public JcAnotacao postMapping() {
		throw new NaoImplementadoException();
	}

	public static final JcTipoProjeto JBoss = new JcTipoProjeto() {

		@Override
		protected JcAnotacao getStateless() {
			return new JcAnotacao("javax.ejb.Stateless");
		}

		@Override
		protected JcAnotacao getCdi() {
			return new JcAnotacao("javax.ejb.EJB");
		}

	};

	public static final JcTipoProjeto Spring = new JcTipoProjeto() {

		@Override
		protected JcAnotacao getStateless() {
			return new JcAnotacao("org.springframework.stereotype.Component");
		}

		@Override
		protected JcAnotacao getApi() {
			return new JcAnotacao("org.springframework.web.bind.annotation.RestController");
		}

		@Override
		protected JcAnotacao getCdi() {
			return new JcAnotacao("org.springframework.beans.factory.annotation.Autowired");
		}

		@Override
		public JcAnotacao pathVariable() {
			return new JcAnotacao("org.springframework.web.bind.annotation.PathVariable");
		}
		
		@Override
		public JcAnotacao getRequestBodyAnnotation() {
			return new JcAnotacao("org.springframework.web.bind.annotation.RequestBody");
		}
		
		@Override
		public JcAnotacao getRequestHeaderAnnotation() {
			return new JcAnotacao("org.springframework.web.bind.annotation.RequestHeader");
		}

		@Override
		public JcAnotacao getMapping() {
			return new JcAnotacao("org.springframework.web.bind.annotation.GetMapping");
		}

		@Override
		public JcAnotacao postMapping() {
			return new JcAnotacao("org.springframework.web.bind.annotation.PostMapping");
		}

	};

	public static final JcTipoProjeto React = new JcTipoProjeto() {

		@Override
		protected JcAnotacao getStateless() {
			return new JcAnotacao("react.support.anotacoes.Component");
		}

	};

	public static JcTipoProjeto selected = Spring;

	public static boolean isSpring() {
		return selected == Spring;
	}

	public static JcAnotacao pathVariable(String nome) {
		return selected.pathVariable().setValue("\"" + nome + "\"");
	}

	public static JcAnotacao requestBody() {
		return selected.getRequestBodyAnnotation();
	}

	public static JcAnotacao requestHeader() {
		return selected.getRequestHeaderAnnotation();
	}
	
	public static JcAnotacao getMapping(String s) {
		return selected.getMapping().setValue("\"" + s + "\"");
	}

	public static JcAnotacao postMapping(String s) {
		return selected.postMapping().setValue("\"" + s + "\"");
	}

}