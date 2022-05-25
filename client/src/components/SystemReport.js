import React, { useEffect } from 'react'
import EventNavbar from './EventNavbar';
import './SystemReport.css';
import axios from "axios";
import moment from "moment";
import { PieChart, Pie, Sector, Cell, Tooltip, ResponsiveContainer } from 'recharts';

function SystemReport() {
  const [paidData, setPaidData] = React.useState([]);
  const [mimicDate, setMimicDate] = React.useState(null);

  useEffect(() => {
    axios
      .get("/api/time")
      .then((res) => {
        // console.log(res.data);
        setMimicDate(moment(res.data).format("YYYY-MM-DD HH:mm"));
        const date = moment(mimicDate).format("YYYY-MM-DD");
        const hourMinute = moment(mimicDate).format("HH:mm");
        const time = date + "T" + hourMinute;

        axios
          .get("/api/report/system", {
            params: {
              time
            },
          })
          .then((res) => {
            console.log(res.data);
            const paidData = [
              { name: 'unpaid Events', value: 100 - res.data.percentPaidEvents },
              { name: 'Paid Events', value: res.data.percentPaidEvents }
            ];

            setPaidData(paidData);
          }).catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  }, [mimicDate]);

  return (
    <div>
      <EventNavbar />
      <div className='report-container'>
        <h1>System Report</h1>
        <ResponsiveContainer width="100%" height={400}>
          <PieChart width={400} height={400}>
            <Pie
              dataKey="value"
              isAnimationActive={false}
              data={paidData}
              cx="50%"
              cy="50%"
              outerRadius={80}
              fill="#8884d8"
              label />
            <Tooltip />
          </PieChart>
        </ResponsiveContainer>
      </div>
    </div>
  )
}

export default SystemReport