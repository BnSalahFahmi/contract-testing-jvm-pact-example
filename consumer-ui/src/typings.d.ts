declare var module: NodeModule;
declare var $: any;

interface NodeModule {
  id: string;
}

interface User {
  id: string;
  fullName: string;
  role: UserRole;
  permissions: Permission[]
}

interface Permission {
  id: string;
  name: string;
}

type UserRole = 'ADMIN' | 'USER';
