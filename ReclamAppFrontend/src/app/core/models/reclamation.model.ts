export type StatutReclamation = 'OUVERTE' | 'EN_COURS' | 'RESOLUE' | 'FERMEE';

export interface Reclamation {
  id?: number;
  produit: string;
  description: string;
  statut: StatutReclamation;
  date?: string;
  note?: number;

  clientId?: number;
  clientNom?: string;

  agentId?: number;
  agentNom?: string;

  selectedAgentId?: number;
}
