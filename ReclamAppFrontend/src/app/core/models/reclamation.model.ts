import { Client } from './client.model';
import { Agent } from './agent.model';

export type StatutReclamation = 'OUVERTE' | 'EN_COURS' | 'RESOLUE' | 'FERMEE';

export interface Reclamation {
  id?: number;
  client: {
    id: number;
    nom: string;
    email: string;
    telephone: string;
  };
  produit: string;
  statut: string;
  description: string;
  date: string;
  note?: number;
  agentSAV?: {
    id: number;
    nom: string;
    competence: string;
  } | null;
}
