import {
  createContext,
  useContext,
  useState,
  type ReactNode,
} from "react";

import { authService } from "../services/authService";

import { tokenService } from "../services/tokenService";

import type { LoginRequest } from "../types/authTypes";

interface AuthContextType {
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
}

const AuthContext =
  createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({
  children,
}: {
  children: ReactNode;
}) {

  const [isAuthenticated, setIsAuthenticated] =
    useState(false);

  async function login(
    credentials: LoginRequest
  ): Promise<void> {

    const response =
      await authService.login(credentials);

    tokenService.setAccessToken(
      response.accessToken
    );

    setIsAuthenticated(true);
  }

  function logout(): void {

    tokenService.clearAccessToken();

    setIsAuthenticated(false);
  }

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {

  const context = useContext(AuthContext);

  if (!context) {
    throw new Error(
      "useAuth must be used inside AuthProvider"
    );
  }

  return context;
}