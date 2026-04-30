import { Client } from './client.model';
import { Agent } from './agent.model';

export type StatutReclamation = 'OUVERTE' | 'EN_COURS' | 'RESOLUE' | 'FERMEE';

export interface Reclamation {
  id?: number;
  client: Client;
  produit: string;
  statut?: StatutReclamation;
  description: string;
  date?: string;
  note?: number | null;
  agentSAV?: Agent | null;
}