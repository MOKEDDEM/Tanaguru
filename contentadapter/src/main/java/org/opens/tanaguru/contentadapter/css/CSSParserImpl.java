package org.opens.tanaguru.contentadapter.css;

import org.opens.tanaguru.contentadapter.util.AbstractContentParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;

/**
 * 
 * @author ADEX
 */
public class CSSParserImpl extends AbstractContentParser implements CSSParser {

    private CSSOMStyleSheet result;

    /**
     * The parser (needed to be injected by spring)
     */
    private Parser parser = null;

    public CSSParserImpl() {
        super();
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            String rsrc = resource.getResource();
            InputSource is = new InputSource(new StringReader(rsrc));

            CSSOMDocumentHandlerImpl handler = new CSSOMDocumentHandlerImpl(
                    (CSSResource) resource);

            parser.setDocumentHandler(handler);
            parser.parseStyleSheet(is);
            result = handler.getResult();
        } catch (CSSException ex) {
            Logger.getLogger(CSSParserImpl.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            Logger.getLogger(CSSParserImpl.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Based on the CSSOMDocumentHandlerImport object, only search
     * imported styleSheet within stylesheet
     * @return
     */
    @Override
    public Set<CSSImportedStyle> searchImportedStyles() {
        try {
            String rsrc = resource.getResource();
            InputSource is = new InputSource(new StringReader(rsrc));
            
            CSSOMDocumentHandlerForImport handler = 
                    new CSSOMDocumentHandlerForImport((CSSResource) resource);

            parser.setDocumentHandler(handler);
            parser.parseStyleSheet(is);

            return handler.getResult();
        } catch (CSSException ex) {
            Logger.getLogger(CSSParserImpl.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            Logger.getLogger(CSSParserImpl.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        }
    }

    public void setParser(Parser parser){
        this.parser = parser;
    }

    @Override
    public CSSOMStyleSheet getResult() {
        return result;
    }

}
