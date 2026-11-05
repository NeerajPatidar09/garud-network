import apiClient from "../../../services/apiClient";

import type {
  LoginRequest,
  AuthResponse,
} from "../types/authTypes";

export const authService = {

  async login(
    credentials: LoginRequest
  ): Promise<AuthResponse> {

    const response =
      await apiClient.post<AuthResponse>(
        "/auth/login",
        credentials
      );

    return response.data;
  },

};