package hu.comperd.befekt.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import hu.comperd.befekt.dto.Domain;

@Document(collection = "domain")
public class DomainCol {
    /** Azonosító. */
	@Id
    private String			id;
    /** Domain csoport kódja. */
    private String 			domCsoportKod;
    /** Domain kódja. */
    private String 			domKod;
    /** Domain megnevezése. */
    private String 			domMegnev;

    public DomainCol() {
    }

    public DomainCol(final Domain domain) {
        this.id = domain.getId();
        this.domCsoportKod = domain.getDomCsoportKod();
        this.domKod = domain.getDomKod();
        this.domMegnev = domain.getDomMegnev();
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String pId) {
        this.id = pId;
    }

    public String getDomCsoportKod() {
		return this.domCsoportKod;
	}

	public void setDomCsoportKod(final String pDomCsoportKod) {
		this.domCsoportKod = pDomCsoportKod;
	}

	public String getDomKod() {
		return this.domKod;
	}

	public void setDcsKod(final String pDomKod) {
		this.domKod = pDomKod;
	}

	public String getDomMegnev() {
		return this.domMegnev;
	}

	public void setDomMegnev(final String pDomMegnev) {
		this.domMegnev = pDomMegnev;
	}

	@Override
    public String toString() {
        return "Domain["
        		+ "id=" + this.id
        		+ ", dom_csoport_kod=" + this.domCsoportKod
        		+ ", dom_kod=" + this.domKod
        		+ ", dom_megnev=" + this.domMegnev
        		+ "]";
    }
}
