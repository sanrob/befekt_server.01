package hu.comperd.befekt.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import hu.comperd.befekt.dto.DomainCsoport;

@Document(collection = "domain_csoportok")
public class DomainCsoportCol {
    /** Azonosító. */
	@Id
    private String			id;
    /** Domain csoport kódja. */
    private String 			dcsKod;
    /** Domain csoport megnevezése. */
    private String 			dcsMegnev;

    public DomainCsoportCol() {
    }

    public DomainCsoportCol(final DomainCsoport domainCsoport) {
        this.id = domainCsoport.getId();
        this.dcsKod = domainCsoport.getDcsKod();
        this.dcsMegnev = domainCsoport.getDcsMegnev();
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String pId) {
        this.id = pId;
    }

    public String getDcsKod() {
		return this.dcsKod;
	}

	public void setDcsKod(final String pDcsKod) {
		this.dcsKod = pDcsKod;
	}

	public String getDcsMegnev() {
		return this.dcsMegnev;
	}

	public void setDcsMegnev(final String pDcsMegnev) {
		this.dcsMegnev = pDcsMegnev;
	}

	@Override
    public String toString() {
        return "DomainCsoport["
        		+ "id=" + this.id
        		+ ", dcs_kod=" + this.dcsKod
        		+ ", dcs_megnev=" + this.dcsMegnev
        		+ "]";
    }
}
