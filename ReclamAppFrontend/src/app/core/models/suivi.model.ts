export interface Suivi {
  id?: number;
  message: string;
  action?: string;
  date?: string;
  reclamation: { id: number };
  agentSAV?: { id: number } | null;
}