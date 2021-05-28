package it.unibo.paserver.domain;

public enum BinaryDocumentType {
	ID_SCAN, LAST_INVOICE, PRIVACY, PRESA_CONSEGNA_SIM, PRESA_CONSEGNA_PHONE;

	public String getFilenameSub() {
		switch (this) {
		case ID_SCAN:
			return "id";
		case LAST_INVOICE:
			return "ultima bolletta";
		case PRESA_CONSEGNA_PHONE:
			return "presa consegna telefono";
		case PRESA_CONSEGNA_SIM:
			return "presa consegna SIM";
		case PRIVACY:
			return "liberatoria privacy";
		default:
			return "";
		}
	}
}