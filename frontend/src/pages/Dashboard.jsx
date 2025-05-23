import TodoList from "../components/TodoList";
import SummaryButton from "../components/SummaryButton";

export default function Dashboard() {
  return (
    <div className="max-w-4xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <div className="space-y-8">
        <div className="text-center">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Task Management</h1>
          <p className="text-gray-600">Organize your daily tasks efficiently</p>
        </div>
        
        <TodoList />
        <SummaryButton />
      </div>
    </div>
  );
}