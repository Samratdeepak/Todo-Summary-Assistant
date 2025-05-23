import { useEffect, useState } from "react";
import API from "../api";
import { FaTrash, FaEdit, FaCheck, FaPlus } from "react-icons/fa";

export default function TodoList() {
  const [todos, setTodos] = useState([]);
  const [editing, setEditing] = useState(null);
  const [title, setTitle] = useState("");
  const [completed, setCompleted] = useState(false);

  const fetchTodos = async () => {
    const res = await API.get("/todos");
    setTodos(res.data);
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  const addTodo = async () => {
    if (!title.trim()) return;
    await API.post("/todos", { title, completed });
    setTitle("");
    setCompleted(false);
    fetchTodos();
  };

  const updateTodo = async (id) => {
    if (!title.trim()) return;
    await API.put(`/todos/${id}`, { title, completed });
    setEditing(null);
    setTitle("");
    setCompleted(false);
    fetchTodos();
  };

  const deleteTodo = async (id) => {
    await API.delete(`/todos/${id}`);
    fetchTodos();
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">My Todo List</h2>
      
      <div className="flex mb-6 items-center bg-gray-50 rounded-lg p-3">
        <input
          className="flex-1 p-3 border border-gray-300 rounded-l-lg focus:outline-none focus:ring-2 focus:ring-green-500"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Add a new task..."
        />
        <div className="flex items-center px-4 bg-gray-100 h-full">
          <input
            type="checkbox"
            id="completed-checkbox"
            className="h-5 w-5 text-green-600 rounded focus:ring-green-500"
            checked={completed}
            onChange={(e) => setCompleted(e.target.checked)}
          />
          <label htmlFor="completed-checkbox" className="ml-2 text-gray-700">
            Completed
          </label>
        </div>
        <button
          className="bg-green-600 text-white p-3 rounded-r-lg hover:bg-green-700 transition-colors flex items-center"
          onClick={editing ? () => updateTodo(editing) : addTodo}
        >
          {editing ? (
            <>
              <FaCheck className="mr-2" /> Update
            </>
          ) : (
            <>
              <FaPlus className="mr-2" /> Add
            </>
          )}
        </button>
      </div>

      <ul className="divide-y divide-gray-200">
        {todos.map((todo) => (
          <li
            className="py-4 flex items-center justify-between"
            key={todo.id}
          >
            <div className="flex items-center">
              <input
                type="checkbox"
                checked={todo.completed}
                readOnly
                className="h-5 w-5 text-green-600 rounded mr-3"
              />
              <span
                className={`${
                  todo.completed ? "line-through text-gray-400" : "text-gray-800"
                }`}
              >
                {todo.title}
              </span>
            </div>
            <div className="flex space-x-3">
              <button
                className="text-blue-600 hover:text-blue-800 transition-colors"
                onClick={() => {
                  setEditing(todo.id);
                  setTitle(todo.title);
                  setCompleted(todo.completed);
                }}
              >
                <FaEdit />
              </button>
              <button
                className="text-red-600 hover:text-red-800 transition-colors"
                onClick={() => deleteTodo(todo.id)}
              >
                <FaTrash />
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}