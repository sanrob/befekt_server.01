package hu.comperd.befekt.dto;

public class Domain {
    /** Azonosító. */
    private String		id;
    /** Domain csoport kódja. */
    private String 		domCsoportKod;
    /** Domain kódja. */
    private String 		domKod;
    /** Domain megnevezése. */
    private String 		domMegnev;

    public Domain() {
    	//
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

	public void setDomKod(final String pDomKod) {
		this.domKod = pDomKod;
	}

	public String getDomMegnev() {
		return this.domMegnev;
	}

	public void setDomMegnev(final String pDomMegnev) {
		this.domMegnev = pDomMegnev;
	}
}
