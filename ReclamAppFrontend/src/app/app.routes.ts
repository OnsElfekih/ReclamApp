import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  {
    path: 'clients',
    loadComponent: () => import('./pages/clients/client-list/client-list')
      .then(m => m.ClientList)
  },
  {
    path: 'clients/nouveau',
    loadComponent: () => import('./pages/clients/client-form/client-form')
      .then(m => m.ClientForm)
  },
  {
    path: 'clients/modifier/:id',
    loadComponent: () => import('./pages/clients/client-form/client-form')
      .then(m => m.ClientForm)
  },
  {
    path: 'agents',
    loadComponent: () => import('./pages/agents/agent-list/agent-list')
      .then(m => m.AgentList)
  },
  {
    path: 'agents/nouveau',
    loadComponent: () => import('./pages/agents/agent-form/agent-form')
      .then(m => m.AgentForm)
  },
{
  path: 'agents/modifier/:id',
  loadComponent: () =>
    import('./pages/agents/agent-form/agent-form')
      .then(m => m.AgentForm)
},
  {
    path: 'reclamations',
    loadComponent: () => import('./pages/reclamations/reclamation-list/reclamation-list')
      .then(m => m.ReclamationList)
  },
  {
    path: 'reclamations/nouvelle',
    loadComponent: () => import('./pages/reclamations/reclamation-form/reclamation-form')
      .then(m => m.ReclamationForm)
  },
  {
    path: 'reclamations/modifier/:id',
    loadComponent: () => import('./pages/reclamations/reclamation-form/reclamation-form')
      .then(m => m.ReclamationForm)
  },
  {
    path: 'reclamations/:id',
    loadComponent: () => import('./pages/reclamations/reclamation-detail/reclamation-detail')
      .then(m => m.ReclamationDetail)
  },
  {
    path: 'rapport',
    loadComponent: () => import('./pages/rapport/rapport')
      .then(m => m.Rapport)
  }
];