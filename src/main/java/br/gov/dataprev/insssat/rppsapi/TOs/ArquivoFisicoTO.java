package br.gov.dataprev.insssat.rppsapi.TOs;

public class ArquivoFisicoTO {

    private String nomeArquivo;
	private String stringArquivo;
    
    
    public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getStringArquivo() {
		return stringArquivo;
	}
	public void setStringArquivo(String stringArquivo) {
		this.stringArquivo = stringArquivo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		result = prime * result + ((stringArquivo == null) ? 0 : stringArquivo.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "ArquivoTO [nomeArquivo=" + nomeArquivo + ", stringArquivo=" + stringArquivo + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoFisicoTO other = (ArquivoFisicoTO) obj;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		if (stringArquivo == null) {
			if (other.stringArquivo != null)
				return false;
		} else if (!stringArquivo.equals(other.stringArquivo))
			return false;
		return true;
	}
}
