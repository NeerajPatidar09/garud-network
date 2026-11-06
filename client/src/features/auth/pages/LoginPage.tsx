import { useState } from "react";

import { useNavigate } from "react-router";

import { useAuth } from "../context/AuthContext";

export default function LoginPage() {

  const { login } = useAuth();

  const navigate = useNavigate();

  const [username, setUsername] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [error, setError] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  async function handleSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {

    event.preventDefault();

    setError("");
    setLoading(true);

    try {

      await login({
        username,
        password,
      });

      navigate("/dashboard", {
        replace: true,
      });

    } catch {

      setError(
        "Invalid username or password."
      );

    } finally {

      setLoading(false);

    }
  }

  return (

    <div className="login-page">

      <h1>Garud Network Login</h1>

      <form onSubmit={handleSubmit}>

        <label htmlFor="username">
          Username
        </label>

        <input
          id="username"
          type="text"
          value={username}
          onChange={(event) =>
            setUsername(event.target.value)
          }
          required
          autoComplete="username"
        />

        <label htmlFor="password">
          Password
        </label>

        <input
          id="password"
          type="password"
          value={password}
          onChange={(event) =>
            setPassword(event.target.value)
          }
          required
          autoComplete="current-password"
        />

        {error && (
          <p role="alert">
            {error}
          </p>
        )}

        <button
          type="submit"
          disabled={loading}
        >
          {loading ? "Signing in..." : "Sign In"}
        </button>

      </form>

    </div>
  );
}