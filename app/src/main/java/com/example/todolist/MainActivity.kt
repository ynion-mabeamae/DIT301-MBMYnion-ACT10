package com.example.todolist

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val todoList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.todoListView)
        val input = findViewById<EditText>(R.id.taskInput)
        val addBtn = findViewById<FloatingActionButton>(R.id.addBtn)

        // READ: Using the 4-argument constructor to prevent the crash
        adapter = ArrayAdapter(this, R.layout.list_item_white_text, android.R.id.text1, todoList)
        listView.adapter = adapter

        // CREATE: Add Task
        addBtn.setOnClickListener {
            val text = input.text.toString().trim()
            if (text.isNotEmpty()) {
                todoList.add(0, text)
                adapter.notifyDataSetChanged()
                input.text.clear()
            }
        }

        // UPDATE: Tap to Edit
        listView.setOnItemClickListener { _, _, position, _ ->
            val editInput = EditText(this)
            editInput.setText(todoList[position])
            editInput.setTextColor(Color.WHITE)
            editInput.setPadding(60, 40, 60, 40)

            MaterialAlertDialogBuilder(this)
                .setTitle("Update Task")
                .setView(editInput)
                .setPositiveButton("Save") { _, _ ->
                    val newText = editInput.text.toString().trim()
                    if (newText.isNotEmpty()) {
                        todoList[position] = newText
                        adapter.notifyDataSetChanged()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // DELETE: Long Press to Remove
        listView.setOnItemLongClickListener { _, _, position, _ ->
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to remove this?")
                .setPositiveButton("Delete") { _, _ ->
                    todoList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }
    }
}