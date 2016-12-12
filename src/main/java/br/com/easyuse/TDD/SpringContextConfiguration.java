package br.com.easyuse.TDD;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do pacote Spring.
 * Esta classe promove a varredura das classes anotadas em todos os sub-pacotes.
 * No pacote br.anhembi.grupoddl.sistemareformas.context você poderá encontrar as 
 * demais configuração de injeção de dependência.
 * Este modelo anotado dispensa arquivos de configuração em XML.
 */
@Configuration
@ComponentScan
public class SpringContextConfiguration {

}
