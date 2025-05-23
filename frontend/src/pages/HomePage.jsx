import { FaTasks, FaChartLine, FaSlack } from "react-icons/fa";
import { MdEmail, MdSecurity } from "react-icons/md";

export default function HomePage() {
  const features = [
    {
      icon: <FaTasks className="text-4xl text-green-600" />,
      title: "Task Management",
      description: "Easily create, update, and organize your daily tasks."
    },
    {
      icon: <FaChartLine className="text-4xl text-green-600" />,
      title: "Productivity Insights",
      description: "Track your progress with comprehensive summaries."
    },
    {
      icon: <MdEmail className="text-4xl text-green-600" />,
      title: "Secure Authentication",
      description: "Email/OTP based secure login system."
    },
    {
      icon: <FaSlack className="text-4xl text-green-600" />,
      title: "Slack Integration",
      description: "Get your task summaries directly in Slack."
    }
  ];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="text-center mb-16">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">
          Welcome to <span className="text-green-600">TodoSummary</span>
        </h1>
        <p className="text-xl text-gray-600 max-w-3xl mx-auto">
          Your personal task management assistant with AI-powered summaries and seamless integrations.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-16">
        {features.map((feature, index) => (
          <div key={index} className="bg-white p-6 rounded-lg shadow-md text-center">
            <div className="flex justify-center mb-4">{feature.icon}</div>
            <h3 className="text-lg font-semibold text-gray-900 mb-2">{feature.title}</h3>
            <p className="text-gray-600">{feature.description}</p>
          </div>
        ))}
      </div>

      <div className="bg-green-50 rounded-lg p-8 text-center">
        <h2 className="text-2xl font-bold text-gray-900 mb-4">Ready to boost your productivity?</h2>
        <p className="text-gray-600 mb-6 max-w-2xl mx-auto">
          Sign up now and start managing your tasks more efficiently with our powerful tools.
        </p>
        <div className="flex justify-center space-x-4">
          <a
            href="/register"
            className="bg-green-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-green-700 transition-colors"
          >
            Get Started
          </a>
          <a
            href="/login"
            className="border border-green-600 text-green-600 px-6 py-3 rounded-lg font-medium hover:bg-green-50 transition-colors"
          >
            Login
          </a>
        </div>
      </div>
    </div>
  );
}