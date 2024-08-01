import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from "react";
import { server } from "../../../app/api";
import { Outlet } from "react-router-dom";

const refreshInterval = 60000 * 30;
const userApi = server + "/user";

const UserContext = createContext({
  user: null,
  error: undefined,
  isLoading: true,
  fetchUser: async () => {},
});

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(undefined);
  const [isLoading, setIsLoading] = useState(true);

  const fetchUser = useCallback(async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      localStorage.removeItem("user");
      console.log("token error");
      return;
    }

    try {
      const header = {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const response = await fetch(userApi, header);
      const responseBody = await response.json();

      if (response.status == 200) {
        const cachedUser = {
          username: responseBody.username,
          createdAt: Date.now(),
        };

        setUser(responseBody);
        localStorage.setItem("user", JSON.stringify(cachedUser));
      } else if (response.status == 403) {
        console.log("unauthorized");
        setUser(null);
        localStorage.removeItem("user");
      }
    } catch (error) {
      console.log("unauthorized");
      setError(error);
      setUser(null);
      localStorage.removeItem("user");
    }
  }, []);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    (async () => {
      if (storedUser && isLoading) {
        setUser(JSON.parse(storedUser));
        await fetchUser();
      }
      setIsLoading(false);
    })();

    if (isLoading) return;

    const onFocus = () => {
      const cachedUser = JSON.parse(localStorage.getItem("user"));
      setUser((user) =>
        cachedUser.username ? { ...user, ...cachedUser } : null
      );
      if (refreshInterval < Date.now() - cachedUser.createdAt) fetchUser();

      window.addEventListener("focus", onFocus);

      return () => window.removeEventListener("focus", onFocus);
    };
  }, [fetchUser, isLoading]);

  const userContextValue = {
    user,
    error,
    isLoading,
    fetchUser,
  };

  return (
    <UserContext.Provider value={userContextValue}>
      {children}
      <Outlet />
    </UserContext.Provider>
  );
};

export default function useUser() {
  return useContext(UserContext);
}
