import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'clients',
    loadComponent: () =>
      import('./pages/clients/client-list/client-list').then((m) => m.ClientList),
  },
  {
    path: 'agents',
    loadComponent: () => import('./pages/agents/agent-list/agent-list').then((m) => m.AgentList),
  },
  {
    path: 'agents/nouveau',
    loadComponent: () => import('./pages/agents/agent-form/agent-form').then((m) => m.AgentForm),
  },
  {
    path: 'agents/modifier/:id',
    loadComponent: () => import('./pages/agents/agent-form/agent-form').then((m) => m.AgentForm),
  },
  {
    path: 'reclamations',
    loadComponent: () =>
      import('./pages/reclamations/reclamation-list/reclamation-list').then(
        (m) => m.ReclamationList,
      ),
  },
  {
    path: 'reclamations/nouvelle',
    loadComponent: () =>
      import('./pages/reclamations/reclamation-form/reclamation-form').then(
        (m) => m.ReclamationForm,
      ),
  },
  {
    path: 'reclamations/modifier/:id',
    loadComponent: () =>
      import('./pages/reclamations/reclamation-form/reclamation-form').then(
        (m) => m.ReclamationForm,
      ),
  },
  {
    path: 'rapport',
    loadComponent: () => import('./pages/rapport/rapport').then((m) => m.Rapport),
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login').then((m) => m.Login),
  },
  {
    path: 'espace-client',
    loadComponent: () => import('./pages/espace-client/espace-client').then((m) => m.EspaceClient),
  },
  {
    path: 'profil-client',
    loadComponent: () => import('./pages/profil-client/profil-client').then((m) => m.ProfilClient),
  },
  {
    path: 'mes-reclamations',
    loadComponent: () =>
      import('./pages/mes-reclamations/mes-reclamations').then((m) => m.MesReclamations),
  },

  {
    path: 'client-reclamations/add',
    loadComponent: () =>
      import('./pages/reclamations/reclamation-form/reclamation-form').then(
        (m) => m.ReclamationForm,
      ),
  },

  {
    path: 'client-reclamations/edit/:id',
    loadComponent: () =>
      import('./pages/reclamations/reclamation-form/reclamation-form').then(
        (m) => m.ReclamationForm,
      ),
  },

  {
    path: 'signup',
    loadComponent: () => import('./pages/signup/signup').then((m) => m.Signup),
  },
];
