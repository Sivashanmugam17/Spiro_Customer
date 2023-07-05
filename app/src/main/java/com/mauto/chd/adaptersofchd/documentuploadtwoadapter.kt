package com.mauto.chd.adaptersofchd

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.R
import com.mauto.chd.data.steptwodocumentdb.documenttwodoRecord
import kotlinx.android.synthetic.main.todo_item.view.*
import org.jetbrains.anko.textColor

class documentuploadtwoadapter( todoEvents: TodoEvents,mcontext:Context) : RecyclerView.Adapter<documentuploadtwoadapter.ViewHolder>()
{

    private var todoList: List<documenttwodoRecord> = arrayListOf()
    private var filteredTodoList: List<documenttwodoRecord> = arrayListOf()
    private val listener: TodoEvents = todoEvents

    private val mcontext: Context = mcontext


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredTodoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(filteredTodoList[position], listener,position,filteredTodoList.size,filteredTodoList,mcontext)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(todo: documenttwodoRecord, listener: TodoEvents,position:Int,fullsize:Int,filteredTodoLists:List<documenttwodoRecord>,mcontext:Context)
        {
            itemView.heading.text = todo.coloumnname
            itemView.notes.text = todo.notes


            if(todo.frontandback.equals("3") && !todo.imagestore.equals("") && !todo.imagestore1.equals("")  && todo.submitedfully.equals("0"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedpendingforapproval)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("3") && !todo.imagestore.equals("") && !todo.imagestore1.equals("")  && todo.submitedfully.equals("1"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedsu)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("3") && todo.imagestore.equals("") && !todo.imagestore1.equals("")  )
            {
                itemView.nofile.text = mcontext.getString(R.string.onemorefilepending)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("3") && !todo.imagestore.equals("") && todo.imagestore1.equals("") )
            {
                itemView.nofile.text = mcontext.getString(R.string.onemorefilepending)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("1") && !todo.imagestore.equals("") && todo.submitedfully.equals("0"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedpendingforapproval)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("1") && !todo.imagestore.equals("") && todo.submitedfully.equals("1"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedsu)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("1") && todo.imagestore.equals(""))
            {
                itemView.nofile.text = mcontext.getString(R.string.nofielchoosen)
                itemView.nofile.textColor = Color.parseColor("#000000")
            }
            else if(todo.frontandback.equals("1") && !todo.imagestore.equals(""))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedpendingforapproval)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("2") && !todo.imagestore1.equals("") && todo.submitedfully.equals("0"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedpendingforapproval)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("2") && !todo.imagestore1.equals("") && todo.submitedfully.equals("1"))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedsu)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else if(todo.frontandback.equals("2") && todo.imagestore1.equals(""))
            {
                itemView.nofile.text = mcontext.getString(R.string.nofielchoosen)
                itemView.nofile.textColor = Color.parseColor("#000000")
            }
            else if(todo.frontandback.equals("2") && !todo.imagestore1.equals(""))
            {
                itemView.nofile.text = mcontext.getString(R.string.fileuploadedpendingforapproval)
                itemView.nofile.textColor = Color.parseColor("#427fed")
            }
            else
            {
                itemView.nofile.text =  mcontext.getString(R.string.nofielchoosen)
                itemView.nofile.textColor = Color.parseColor("#000000")
            }


            itemView.choosefile.text = mcontext.getString(R.string.choosefile)






            if(todo.is_req.equals("1"))
                itemView.mandatory.visibility = View.VISIBLE
            else
                itemView.mandatory.visibility = View.GONE



            itemView.choosefile.setOnClickListener {
                listener.onDeleteClicked(todo)
            }



            itemView.nextlayout.setOnClickListener {
                listener.onSendClicked(filteredTodoLists)
            }
            itemView.next.setOnClickListener {
                listener.onSendClicked(filteredTodoLists)
            }


            itemView.choosefile.setOnClickListener {
                listener.onDeleteClicked(todo)
            }





            if((fullsize-1)==position && todo.uuid.equals(""))
            {
                itemView.nextlayout.visibility = View.VISIBLE
            }
            else
            {
                itemView.nextlayout.visibility = View.GONE
            }
        }
    }

    fun setAllTodoItems(todoItems: List<documenttwodoRecord>)
    {
        this.todoList = todoItems
        this.filteredTodoList = todoItems
        notifyDataSetChanged()
    }



    interface TodoEvents
    {
        fun onDeleteClicked(todoRecord: documenttwodoRecord)
        fun onSendClicked(todoItems: List<documenttwodoRecord>)

    }
}