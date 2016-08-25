import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import groovy.ui.OutputTransforms
import groovy.util.logging.Slf4j

import javax.swing.ImageIcon
import java.awt.Toolkit
import java.awt.image.ImageProducer

@Slf4j
class Evaluator {

    Binding binding = []

    GroovyShell shell = [binding]

    def macros = [:]

    Evaluator() {
        binding.variables._outputTransforms = []
        binding.variables._outputTransforms += { it -> if (it instanceof ImageProducer) new ImageIcon(Toolkit.defaultToolkit.createImage(it)) }
        binding.variables._outputTransforms += OutputTransforms.loadOutputTransforms()

        binding.variables._ui = new SwingBuilder()
        binding.variables._xml = new XmlSlurper()
        binding.variables._json = new JsonSlurper()

        binding.variables._ws = [:]
        binding.variables._ws.currency = new WebService(wsdl: "http://www.webservicex.net/CurrencyConvertor.asmx?WSDL")
        binding.variables._ws.market = new WebService(wsdl: "http://www.webservicex.net/stockquote.asmx?WSDL")
        binding.variables._ws.whois = new WebService(wsdl: "http://www.webservicex.net/whois.asmx?WSDL")
        binding.variables._ws.units = new WebService(wsdl: "http://www.webservicex.net/ConvertCooking.asmx?WSDL")
        binding.variables._ws.geo = new WebService(wsdl: "http://www.webservicex.net/geoipservice.asmx?WSDL")
        binding.variables._ws.barcode = new WebService(wsdl: "http://www.webservicex.net/barcode.asmx?WSDL")

        loadMacros()

    }

    def evaluate = { expression ->
        log.debug "Evaluating: $expression"

        def result = shell.evaluate(new GroovyCodeSource(expression, 'EvaluationScripts', '/evaluations/script'))
        evaluations << new Evaluation(input: expression, result: OutputTransforms.transformResult(result, shell.context._outputTransforms))
        evaluations[-1].result
    }

    def reevaluate = { index ->
        if (evaluations[index - 1]) {
            evaluate evaluations[index - 1].input
        }
        else {
            println 'No such expression.'
        }
    }

    def console() {
        while (true) {
            def input = System.console().readLine('> ')
            switch(input) {
                case 'exit': return

                case 'list': listMacros(); break

                case 'history': showHistory(); break;

                case ~/history (\d)/:
                    try {
                        def result = reevaluate(input - ~/history / as int)
                        println result
                    }
                    catch (Exception e) {
                        println e.message
                    }
                    break

                default:
                    try {
                        def result = evaluate(input)
                        println result
                    }
                    catch (Exception e) {
                        println e.message
                    }
            }
        }
    }

    def evaluations = []

    private void loadMacros() {
        ConfigSlurper loader = []
        File macrosDir = ['resources/macros']
        macrosDir.list({ dir, filename -> filename.endsWith '.groovy' } as FilenameFilter).each { filename ->
            ConfigObject synonyms = loader.parse(new File(macrosDir, filename).text)
            String namespace = filename - '.groovy'
            synonyms.entrySet().each {
                macros[it.key] = new Synonym(description: it.value.description, expression: it.value.expression)
            }
//            binding.variables[namespace] = macros
            for (synonym in macros.entrySet()) {
//                try {
//                    evaluate("${synonym.key} = ${synonym.value.expression}")
//                }
//                catch (Exception e) {
//                    log.log unsuccessful_eval, e
//                    log.info 'Unsuccessful evaluation: ' + e.message
//                }
                binding.variables[synonym.key] = synonym.value.expression
            }
        }
    }

    private void listMacros() {
        macros.entrySet().each {
            println "$it.key: ${it.value.description ?: 'No description'}"
        }
    }

    private void showHistory() {
        int i = 0
        evaluations.each {
            println "${++i}: [$it.input = $it.result]"
        }
    }
}
