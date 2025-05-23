import { Link, useLocation, useNavigate } from "react-router-dom";
import { FaUserCircle, FaHome, FaClipboardList } from "react-icons/fa";
import { MdDashboard, MdOutlineRestaurantMenu, MdSummarize } from "react-icons/md";
import { FiLogIn, FiLogOut } from "react-icons/fi";
import { GiMeal } from "react-icons/gi";

export default function Navbar({ isLoggedIn, setIsLoggedIn }) {
  const location = useLocation();
  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setIsLoggedIn(false);
    navigate("/");
  };

  const navLinkClass = (path) =>
    location.pathname === path
      ? "text-green-600 font-semibold"
      : "text-gray-700 hover:text-green-600 transition-colors";

  return (
    <nav className="bg-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <div className="flex items-center">
            <Link to="/" className="flex items-center">
              <MdSummarize className="h-8 w-8 text-green-600" />
              <span className="ml-2 text-xl font-bold text-gray-900">
                Todo<span className="text-green-600">Summary</span>
              </span>
            </Link>
          </div>

          <div className="hidden md:flex items-center space-x-8">
            {isLoggedIn && (
              <>
                <Link
                  to="/home"
                  className={`${navLinkClass("/home")} flex items-center`}
                  title="Home"
                >
                  <FaHome className="mr-1" /> Home
                </Link>
                <Link
                  to="/dashboard"
                  className={`${navLinkClass("/dashboard")} flex items-center`}
                  title="Dashboard"
                >
                  <MdDashboard className="mr-1" /> Dashboard
                </Link>
              </>
            )}
          </div>

          <div className="flex items-center">
            {isLoggedIn ? (
              <div className="flex items-center space-x-4">
                {username && (
                  <div className="flex items-center text-gray-700">
                    <FaUserCircle className="mr-1" /> {username}
                  </div>
                )}
                <button
                  onClick={handleLogout}
                  className="flex items-center text-gray-700 hover:text-green-600 transition-colors"
                >
                  <FiLogOut className="mr-1" /> Logout
                </button>
              </div>
            ) : (
              <Link
                to="/login"
                className="flex items-center text-gray-700 hover:text-green-600 transition-colors"
              >
                <FiLogIn className="mr-1" /> Login
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}