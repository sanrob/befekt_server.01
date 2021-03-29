package hu.comperd.befekt.dto;

public class DomainCsoport {
    /** Azonosító. */
    private String		id;
    /** Domain csoport kódja. */
    private String 		dcsKod;
    /** Domain csoport megnevezése. */
    private String 		dcsMegnev;

    public DomainCsoport() {
    	//
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
}
