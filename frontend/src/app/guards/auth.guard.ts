import { AuthGuardData, createAuthGuard } from 'keycloak-angular';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { inject } from '@angular/core';

const isAccessAllowed = async (
    route: ActivatedRouteSnapshot,
    __: RouterStateSnapshot,
    authData: AuthGuardData
): Promise<boolean | UrlTree> => {
    const { authenticated, grantedRoles } = authData;

    const requiredRoles: string[] = route.data['roles'];
    if (!authenticated || !requiredRoles || requiredRoles.length === 0) {
        return inject(Router).parseUrl('/forbidden');
    }

    const hasAnyRequiredRole = requiredRoles.some((role) =>
        grantedRoles.realmRoles.includes(role)
    );

    if (hasAnyRequiredRole) {
        return true;
    }

    return inject(Router).parseUrl('/forbidden');
};

export const canActivateAuthRole: CanActivateFn = createAuthGuard(isAccessAllowed);