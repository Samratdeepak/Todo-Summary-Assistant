import { useState } from "react";
import API from "../api";
import { FaSlack } from "react-icons/fa";
import { FiSend } from "react-icons/fi";

export default function SummaryButton() {
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState("");
  const handleClick = async () => {
    setLoading(true);
    setMsg("");
    try {
      const res = await API.post("/summarize");
      setMsg(res.data?.message || "Summary sent to Slack!");
    } catch (err) {
      setMsg(err.response?.data?.message || "Failed to send summary.");
    }
    setLoading(false);
  };
  return (
    <div className="mt-6">
      <button
        className={`flex items-center px-6 py-3 rounded-lg font-medium ${
          loading
            ? "bg-green-400 cursor-not-allowed"
            : "bg-green-600 hover:bg-green-700"
        } text-white shadow-md transition-colors`}
        onClick={handleClick}
        disabled={loading}
      >
        {loading ? (
          <>
            <svg
              className="animate-spin -ml-1 mr-3 h-5 w-5 text-white"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                className="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                strokeWidth="4"
              ></circle>
              <path
                className="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
              ></path>
            </svg>
            Summarizing...
          </>
        ) : (
          <>
            <FaSlack className="mr-2" /> Summarize & Send to Slack
          </>
        )}
      </button>
      {msg && (
        <div
          className={`mt-3 p-3 rounded-lg ${
            msg.includes("Failed") ? "bg-red-100 text-red-700" : "bg-green-100 text-green-700"
          }`}
        >
          {msg}
        </div>
      )}
    </div>
  );
}