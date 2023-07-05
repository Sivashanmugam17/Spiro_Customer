package com.mauto.chd.adaptersofchd

//
//class Weeklyadapter(val context : Context, private val earning: List<WeekModel>, private val listener: tripdetailclick) : RecyclerView.Adapter<Weeklyadapter.ViewHolder>()
//{
//    override fun onBindViewHolder(holder: ViewHolder, position: Int)
//    {
//        holder?.weeks?.text = earning[position].weeks
//        holder?.netfareamount?.text = earning[position].netfare
//        holder?.tripcount?.text = earning[position].tripscount
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
//    {
//        val v = LayoutInflater.from(p0?.context).inflate(R.layout.weeklyreportadapter, p0, false)
//        return ViewHolder(v)
//    }
//    override fun getItemCount(): Int
//    {
//        return earning.size
//    }
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        val weeks = itemView.findViewById<TextView>(R.id.weeks)
//        val netfareamount = itemView.findViewById<TextView>(R.id.netfareamount)
//        val tripcount = itemView.findViewById<TextView>(R.id.tripcount)
//
//    }
//}