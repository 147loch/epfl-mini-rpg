package ch.epfl.cs107.play.game.arpg.keybindings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class XMLBindings {

    private final static String FILE_PATH = "res/keybindings.xml";

    private Map<String, XMLBinding> bindings;

    /**
     * Upon instantiation, we initialize the linked file and save the contents in a Map
     */
    XMLBindings() {
        this.bindings = new HashMap<>();
        this.init();
    }

    /**
     * Initialization method, what we do is pretty straight-forward:
     *  - initialize the builder factory
     *  - parse the document as file
     *      - if doesn't exist: create the XML file and sync it with the newly created DOM
     *      - if it exists: simply parse it, it will be reparsed if the file was newly created
     *  - pase all the binding elements inside the bindings, create the XMLbinding, and add it to the map
     */
    private void init() {
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                Document newDom = db.newDocument();
                newDom.appendChild(newDom.createElement("bindings"));
                transformXML(newDom);
            }

            dom = db.parse(file);
            dom.getDocumentElement().normalize();

            NodeList bindings = dom.getElementsByTagName("binding");

            for (Node binding: iterable(bindings)) {
                if (binding.hasAttributes()) {
                    NamedNodeMap attr = binding.getAttributes();
                    String name = attr.getNamedItem("name").getTextContent();
                    int keyId = Integer.parseInt(attr.getNamedItem("key").getTextContent());

                    if (name != null && keyId != -1)
                        this.bindings.put(name, new XMLBinding(name, keyId));
                }
            }
        } catch (SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * @return Get all the bindings in a copied HashMap for encapsulation purposes
     */
    Map<String, XMLBinding> getBindings() {
        return new HashMap<>(bindings);
    }

    /**
     * @param name The name of the binding to return
     * @return The biding extracted from a copied HashMap
     */
    XMLBinding getBinding(String name) {
        return getBindings().get(name);
    }

    /**
     * Replace a binding, if it does not exist, add it.
     *
     * Again, what we do is pretty straight-forward:
     *  - initialize the builder factory
     *  - parse the document as file
     *      - parse all of the bindings
     *      - if the binding is found, replace it
     *  - apply the changes to the XML file
     *  - update the map
     *
     * @param name Name of the binding to change
     * @param keyId The new key to replace the previous one
     */
    void setBinding(String name, int keyId) {
        if (!this.bindings.containsKey(name)) {
            this.addBinding(name, keyId);
            return;
        }

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(FILE_PATH);
            dom.getDocumentElement().normalize();

            NodeList bindings = dom.getElementsByTagName("binding");

            for (Node _binding: iterable(bindings)) {
                NamedNodeMap attr = _binding.getAttributes();
                if (attr.getNamedItem("name").getTextContent().equals(name))
                    attr.getNamedItem("key").setTextContent(String.valueOf(keyId));
            }

            transformXML(dom);

            this.bindings.replace(name, new XMLBinding(name, keyId));
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
    /**
     * @param binding The binding to replace
     * @see this#setBinding(String, int)
     * @see XMLBinding#XMLBinding(String, int)
     */
    void setBinding(XMLBinding binding) { setBinding(binding.getName(), binding.getKeyId()); }
    /**
     * @param bindings The list of bindings to replace
     * @see this#setBinding(String, int)
     * @see XMLBinding#XMLBinding(String, int)
     */
    void setBindings(List<XMLBinding> bindings) { bindings.forEach(this::setBinding); }

    /**
     * Add a binding.
     *
     * Again, what we do is pretty straight-forward:
     *  - initialize the builder factory
     *  - parse the document as file
     *      - create a new binding element
     *      - add the corresponding attributes
     *      - add it to the root bindings node
     *  - apply the changes to the XML file
     *  - update the map
     *
     * @param name Name of the binding to change
     * @param keyId The new key to replace the previous one
     */
    void addBinding(String name, int keyId) {
        if (this.bindings.containsKey(name))
            throw new IllegalArgumentException("The binding you want to add already exists.");

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(FILE_PATH);
            dom.getDocumentElement().normalize();

            Element newBinding = dom.createElement("binding");
            newBinding.setAttribute("name", name);
            newBinding.setAttribute("key", String.valueOf(keyId));

            dom.getFirstChild().appendChild(newBinding);

            transformXML(dom);

            this.bindings.put(name, new XMLBinding(name, keyId));
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
    /**
     * @param binding The binding to add
     * @see this#addBinding(String, int)
     * @see XMLBinding#XMLBinding(String, int)
     */
    void addBinding(XMLBinding binding) { addBinding(binding.getName(), binding.getKeyId()); }
    /**
     * @param bindings The list of bindings to add
     * @see this#addBinding(String, int)
     * @see XMLBinding#XMLBinding(String, int)
     */
    void addBindings(List<XMLBinding> bindings) { bindings.forEach(this::addBinding); }

    /**
     * Remove a binding.
     *
     * Again, what we do is pretty straight-forward:
     *  - initialize the builder factory
     *  - parse the document as file
     *      - parse all of the bindings
     *      - if the binding is found, remove it
     *  - apply the changes to the XML file
     *  - update the map
     *
     * @param name Name of the binding to change
     */
    void removeBinding(String name) {
        if (!this.bindings.containsKey(name))
            throw new IllegalArgumentException("The binding you want to remove does not exist.");

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(FILE_PATH);
            dom.getDocumentElement().normalize();

            NodeList bindings = dom.getElementsByTagName("binding");

            for (Node _binding: iterable(bindings)) {
                NamedNodeMap attr = _binding.getAttributes();
                if (attr.getNamedItem("name").getTextContent().equals(name)) {
                    dom.getFirstChild().removeChild(_binding);
                }
            }

            transformXML(dom);

            this.bindings.remove(name);
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
    /**
     * @param binding The binding to remove
     * @see this#removeBinding(String)
     * @see XMLBinding#XMLBinding(String, int)
     */
    void removeBinding(XMLBinding binding) { removeBinding(binding.getName()); }

    /**
     * A basic method to apply the changes done to the XML DOM here to the corresponding XML file.
     *
     * We just initialize a transformer factory, give it the DOM source,
     * stream the result to the file and apply the transformer transform.
     *
     * @param dom The dom that had been edited loacally
     * @throws TransformerException Standard Transformer Exceptions
     */
    private void transformXML(Document dom) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(dom);
        StreamResult result = new StreamResult(new File(FILE_PATH));
        transformer.transform(source, result);
    }

    /**
     * We define here an Iterable, so that we can easily foreach through
     * the NodeList items, which are a weirdly defined.
     * @param nodeList The node list to iterate over
     * @return An iterable compatible with foreach
     */
    private static Iterable<Node> iterable(final NodeList nodeList) {
        return () -> new Iterator<Node>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            @Override
            public Node next() {
                if (!hasNext()) throw new NoSuchElementException();
                return nodeList.item(index++);
            }
        };
    }

}
