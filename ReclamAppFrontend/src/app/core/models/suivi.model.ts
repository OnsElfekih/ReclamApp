import { Reclamation } from './reclamation.model';
import { Agent } from './agent.model';

export interface Suivi {
  id?: number;
  message: string;
  action: string;
  date: string;
  reclamation: Reclamation;
  agentSAV: Agent;
}
