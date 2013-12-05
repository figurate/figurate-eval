import groovy.ui.OutputTransforms

import javax.swing.ImageIcon
import java.awt.Toolkit
import java.awt.image.ImageProducer

class Evaluator {

    Binding binding = []

    GroovyShell shell = [binding]

    Evaluator() {
        binding.variables._outputTransforms = []
        binding.variables._outputTransforms += { it -> if (it instanceof ImageProducer) new ImageIcon(Toolkit.defaultToolkit.createImage(it)) }
        binding.variables._outputTransforms += OutputTransforms.loadOutputTransforms()

        loadSynonyms()
    }

    def evaluate = { expression ->
        def result = shell.evaluate(new GroovyCodeSource(expression, 'EvaluationScripts', '/evaluations/script'))
        OutputTransforms.transformResult result, shell.context._outputTransforms
    }

    def evaluations = []

    private void loadSynonyms() {
        ConfigSlurper loader = []
        ConfigObject synonyms = loader.parse(new File('resources/synonyms.groovy').text)
        synonyms.entrySet().each {
            binding.variables[it.key] = it.value
        }
    }

    static void main(def args) {
        println new Evaluator().evaluate(args[0])
    }
}