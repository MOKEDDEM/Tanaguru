package org.opens.tanaguru.contentadapter.css;

import org.opens.tanaguru.contentadapter.ContentParser;
import org.opens.tanaguru.contentadapter.Resource;
import org.opens.tanaguru.contentadapter.js.AbstractContentAdapter;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import org.opens.tanaguru.contentadapter.util.ExternalRsrc;
import org.opens.tanaguru.contentadapter.util.HtmlNodeAttr;
import org.opens.tanaguru.contentadapter.util.HtmlTags;
import org.opens.tanaguru.contentadapter.util.InlineRsrc;
import org.opens.tanaguru.contentadapter.util.LocalRsrc;
import com.thoughtworks.xstream.XStream;
import java.util.HashSet;
import org.w3c.css.sac.SACMediaList;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.LocatorImpl;

public class CSSContentAdapterImpl extends AbstractContentAdapter implements
        CSSContentAdapter, ContentHandler {

    private StringBuffer buffer;
    private Set<CSSOMStyleSheet> cssSet;
    private Set importedCssVector;
    private Set cssVector;
    private boolean isExternalCSS = false;
    private boolean isInlineCSS = false;
    private boolean isLocalCSS = false;
    private Locator locator;
    private CSSParser parser;
    private String currentLocalResourcePath;
    private final String HTTP_PREFIX = "http";
    private final String WWW_PREFIX = "www";

    /**
     * Default constructor.
     */
    public CSSContentAdapterImpl() {
        super();
    }

    /**
     * @param ch
     * @param start
     * @param end
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int end) throws SAXException {

        if (isLocalCSS) {
            buffer.append(new String(ch, start, end).trim());
        }
    }

    /**
     * Event fired at the end of the document parse
     *
     * @throws SAXException
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
        if (resource != null) {
            resource.addAllResource(cssVector);
            if (importedCssVector != null)
                resource.addAllResource(importedCssVector);

            // At the end of the document, parse and get the result for each
            // found resource
            for (Object object : resource.getResourceSet()) {
                Resource r = (Resource) object;
                if (r.getResource() != null) {
                    parser.setResource(r);
                    parser.run();
                    cssSet.add(parser.getResult());
                }
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String nameSpaceURI, String localName, String rawName)
            throws SAXException {

        if (isLocalCSS){
            resource.setResource(buffer.toString());
        }

        if ((isLocalCSS) || (isExternalCSS) || isInlineCSS) {
            if (resource != null) {
                cssVector.add(resource);
                // search imported resource from the resource
                getImportedResources(resource, currentLocalResourcePath);
                isLocalCSS = false;
                isExternalCSS = false;
                isInlineCSS = false;
            }

        }
    }

    /**
     * @param prefixe
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        // Not used
    }

    public String getAdaptation() {
        return new XStream().toXML(cssSet);
    }

    /**
     * @param ch
     * @param start
     * @param end
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int end)
            throws SAXException {
        // Not used
    }

    /**
     * @param target
     * @param data
     *            d'une
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
     *      java.lang.String)
     */
    public void processingInstruction(String target, String data)
            throws SAXException {
        // Not used
    }

    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    public void setParser(ContentParser parser) {
        this.parser = (CSSParser) parser;
    }

    /**
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String arg0) throws SAXException {
        // Not used
    }

    /**
     * Event fired when the parse starts
     *
     * @throws SAXException
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        locator = new LocatorImpl();
        cssVector = new HashSet();
        buffer = new StringBuffer();
        cssSet = new HashSet<CSSOMStyleSheet>();
    }

    /**
     * @param nameSpaceURI
     * @param localName
     * @param rawName
     * @throws SAXException
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String nameSpaceURI, String localName,
            String rawName, Attributes attributs) throws SAXException {

        buffer.setLength(0);

        // localCSS
        if (HtmlTags.STYLE.equalsIgnoreCase(rawName)) {
            isLocalCSS = true;
            resource = new CSSResourceImpl(null, locator.getLineNumber(),
                    new LocalRsrc());
            buffer.delete(0, buffer.capacity());
            currentLocalResourcePath = urlIdentifier.resolve(".").toExternalForm();
        }
        // externalCSS
        if (HtmlNodeAttr.LINK.equalsIgnoreCase(rawName)) {

            String rel = attributs.getValue(HtmlNodeAttr.REL);

            if ("stylesheet".equalsIgnoreCase(rel)) {
                isExternalCSS = true;
                // resolve the css relative path to its absolute path and add it
                // to the external css links collection
                String cssRelativePath = attributs.getValue(HtmlNodeAttr.HREF);

                resource = getExternalResource(cssRelativePath,
                        getListOfMediaFromAttributeValue(attributs.getValue(HtmlNodeAttr.MEDIA)),
                        false);
            }
        }

        // look up for inlineCSS
        for (int i = 0; i < attributs.getLength(); i++) {
            String attrValue = attributs.getValue(i);
            String attrName = attributs.getQName(i);
            if (HtmlNodeAttr.STYLE.equalsIgnoreCase(attrName)) {
                isInlineCSS = true;
                String cssString = rawName + "{" + attrValue + "}";
                resource = new CSSResourceImpl(cssString, locator.getLineNumber(), new InlineRsrc());
                cssVector.add(resource);
                currentLocalResourcePath = urlIdentifier.resolve(".").toExternalForm();
                break;
            }
        }

    }

    /**
     * @param prefix
     *            .
     * @param URI
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void startPrefixMapping(String prefix, String URI)
            throws SAXException {
        // Not used
    }

    /**
     * Get the list of media from the media attribute content
     * @param mediaAttribute
     * @return
     */
    private SACMediaList getListOfMediaFromAttributeValue(String mediaAttribute) {
        if (mediaAttribute == null) {
            return null;
        } else {
            String localMedia = mediaAttribute.replaceAll("\\s", "");
            SACMediaList mediaList = new SACMediaListImpl();
            for(String media : localMedia.split(",")) {
                ((SACMediaListImpl)mediaList).addItem(media);
            }
            return mediaList;
        }
    }

    /**
     * Get the current resource path (needed for relative file access)
     * @param absolutePath
     * @return
     */
    private String getCurrentResourcePath(String absolutePath){
        int endIndex = absolutePath.lastIndexOf('/');
        String path = absolutePath.substring(0, endIndex+1);
        return path;
    }

    /**
     * Downloads an external resource and returns a Resource instance
     * @param cssRelativePath
     * @param sacMediaList
     * @param importedFromCss
     * @return
     */
    private Resource getExternalResource(String cssRelativePath,
            SACMediaList sacMediaList, boolean importedFromCss) {
        Resource externalResouce = null;

        String cssAbsolutePath =
                urlIdentifier.resolve(cssRelativePath).toExternalForm();

        downloader.setURL(cssAbsolutePath);
        downloader.run();
        if (!importedFromCss) {
            // In case of external resource found when parsing the html,
            // the path of the resource is saved for imports with relative path
            currentLocalResourcePath = getCurrentResourcePath(cssAbsolutePath);
        }
        if (sacMediaList != null) {
            externalResouce = new CSSResourceImpl(downloader.getResult(),
                    locator.getLineNumber(), new ExternalRsrc(),sacMediaList);
        } else {
            externalResouce = new CSSResourceImpl(downloader.getResult(),
                    locator.getLineNumber(), new ExternalRsrc());
        }
        return externalResouce;
    }

    /**
     * Search and download imported resources from resources found in the html
     * Can be call recursively if an imported stylesheet is defined within an
     * imported stylesheet
     * @param resource
     * @param path
     *          The resource path
     */
    private void getImportedResources(Resource resource, String path) {
        Set<CSSImportedStyle> importedStyles =
                new HashSet<CSSImportedStyle>();
        do {
            if (resource.getResource() != null) {

            //Get all the @import refences found in the resource
            parser.setResource(resource);
            importedStyles = parser.searchImportedStyles();

            if (!importedStyles.isEmpty())
                // for each foudn imported resource within a resource
                for (CSSImportedStyle cssImportedStyle : importedStyles) {

                    //build the resource path
                    //If the path is relative, build in it from the path of the
                    // current resource
                    String resourcePath = cssImportedStyle.getPath();
                    if (!resourcePath.startsWith(HTTP_PREFIX)
                        && !resourcePath.startsWith(WWW_PREFIX)) {
                        resourcePath = path +
                            cssImportedStyle.getPath();
                    }

                    // create an instance for the
                    Resource importedResource = getExternalResource(
                            resourcePath,
                            cssImportedStyle.getSACMediaList(), true);
                    
                    // check if the imported resource handle an imported resource
                    // by a recursive call to getImportResources
                    getImportedResources(importedResource, 
                            getCurrentResourcePath(resourcePath));
                    
                    addImportedResource(importedResource);
                }
                importedStyles.clear();
            }
        } while (!importedStyles.isEmpty());
    }

    /**
     * Add an "from css imported" resource to the vector
     * @param importedResource
     */
    private void addImportedResource(Resource importedResource) {
        if (importedCssVector == null) {
            importedCssVector = new HashSet();
        }
        importedCssVector.add(importedResource);
    }

}

